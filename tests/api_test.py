import requests
import json
import datetime

import time 


parameter_url = 'http://localhost:8081/parameters'
get_latest_url = 'http://localhost:8081/parameters/latest'
get_aggregated_url = 'http://localhost:8081/parameters/aggregated/{minutes}'
events_url = 'http://localhost:8081/parameters/events'

def test_emptys():
    r = requests.get(get_latest_url)
    data = r.json()
    assert r.status_code == 200
    assert len(data) == 0

    req = requests.get(get_aggregated_url.format(minutes=5))
    data = req.json()
    assert req.status_code == 200
    assert len(data) == 0

def test_post_malformed():
    parameters = {"heat": 3.0, "moisture": 20.0}
    r = requests.post(parameter_url, json=parameters)
    assert r.status_code == 400

    parameters = {"machineKey": {"asdf": "asdf"}, "parameters": {}}
    r = requests.post(parameter_url, json=parameters)
    assert r.status_code == 400

def test_malformed_with_valids():
    parameters = {"heat": 3}
    paramEvent = {"machineKey": "flufferizer", "parameters": parameters}
    r = requests.post(parameter_url, json=paramEvent)
    assert r.status_code == 201

    parameters = {"machineKey": "flufferizer", "parameters": {"heat": "heat2"}}
    r = requests.post(parameter_url, json=parameters)
    assert r.status_code == 400

    parameters = {"machineKey": "flufferizer", "parameters": {"moisture": "moist1"}}
    r = requests.post(parameter_url, json=parameters)
    assert r.status_code == 400

    r = requests.get(get_latest_url)
    data = r.json()
    assert r.status_code == 200
    assert float(data[0]['value']) == 3.0
    

def test_1():
    machineKey = "flufferizer"
    for i in range(10):
        parameters = {"heat": 3.0 + i, "moisture": 20.0 + i}
        paramEvent = {"machineKey": machineKey, "parameters": parameters}
        r = requests.post(parameter_url, json=paramEvent)
        assert r.status_code == 201

    r = requests.get(get_latest_url)
    response_data = r.json()
    print("Latest output: ")
    print("")
    __import__('pprint').pprint(response_data)

    # Test Latest
    assert len(response_data) == 2
    heat_param = list(filter(lambda x: x['parameter']['key'] == 'heat', response_data))
    assert heat_param[0]['value'] == "12.0"
    moisture_param = list(filter(lambda x: x['parameter']['key'] == 'moisture', response_data))
    assert moisture_param[0]['value'] == "29.0"

    # Test Aggregation
    r = requests.get(get_aggregated_url.format(minutes=5))
    assert r.status_code == 200
    response_data = r.json()
    print("Aggregation output: ")
    print("")
    __import__('pprint').pprint(response_data)

    assert len(response_data) == 2
    heat_param = list(filter(lambda x: x['parameter']['key'] == 'heat', response_data))[0]
    assert heat_param['max'] == 12.0
    assert heat_param['min'] == 3.0
    assert heat_param['median'] == 7.5
    assert heat_param['average'] == 7.5

    moisture_param = list(filter(lambda x: x['parameter']['key'] == 'moisture', response_data))[0]
    assert moisture_param['max'] == 29.0
    assert moisture_param['min'] == 20.0
    assert moisture_param['median'] == 24.5
    assert moisture_param['average'] == 24.5

def test_2_aggregation_in_time_window():
    machineKey = "flufferizer"

    startTime = datetime.datetime.now().isoformat()
    for i in range(10):
        parameters = {"heat": 3.0 + i, "moisture": 20.0 + i}
        paramEvent = {"machineKey": machineKey, "parameters": parameters}
        r = requests.post(parameter_url, json=paramEvent)
        assert r.status_code == 201
        time.sleep(1)
    endTime = datetime.datetime.now().isoformat()
    time.sleep(1)

    for i in range(10, 20):
        parameters = {"heat": 3.0 + i, "moisture": 20.0 + i}
        paramEvent = {"machineKey": machineKey, "parameters": parameters}
        r = requests.post(parameter_url, json=paramEvent)
        assert r.status_code == 201

    r = requests.get(get_latest_url)
    response_data = r.json()
    print("Latest output: ")
    print("")
    __import__('pprint').pprint(response_data)

    # Test Latest
    assert len(response_data) == 2
    heat_param = list(filter(lambda x: x['parameter']['key'] == 'heat', response_data))
    assert heat_param[0]['value'] == "22.0"
    moisture_param = list(filter(lambda x: x['parameter']['key'] == 'moisture', response_data))
    assert moisture_param[0]['value'] == "39.0"

    # Test Aggregation
    r = requests.get(get_aggregated_url.format(minutes=""), params={"startDate":startTime, "endDate":endTime})
    assert r.status_code == 200
    response_data = r.json()
    print("Aggregation output: ")
    print("")
    __import__('pprint').pprint(response_data)

    assert len(response_data) == 2
    heat_param = list(filter(lambda x: x['parameter']['key'] == 'heat', response_data))[0]
    assert heat_param['max'] == 12.0
    assert heat_param['min'] == 3.0
    assert heat_param['median'] == 7.5
    assert heat_param['average'] == 7.5

    moisture_param = list(filter(lambda x: x['parameter']['key'] == 'moisture', response_data))[0]
    assert moisture_param['max'] == 29.0
    assert moisture_param['min'] == 20.0
    assert moisture_param['median'] == 24.5
    assert moisture_param['average'] == 24.5

def delete_all_request():
    r = requests.delete(events_url)
    assert r.status_code == 200


def main():
    print("Test some Empty Requests and Malformed Requests...")
    print("")
    delete_all_request()
    test_emptys()
    test_post_malformed()
    print("Test Malformed Mixed with Normal Reuqests")
    print("")
    delete_all_request()
    test_malformed_with_valids()
    print("Test 1: Starting")
    print("")
    delete_all_request()
    test_1();
    print("Test 2: Starting")
    print("")
    delete_all_request()
    test_2_aggregation_in_time_window();

if __name__ == "__main__":
    main()
