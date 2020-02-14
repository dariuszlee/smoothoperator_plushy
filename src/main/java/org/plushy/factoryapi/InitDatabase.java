package org.plushy.factoryapi;

import org.plushy.factoryapi.models.Machine;
import org.plushy.factoryapi.models.Parameter;
import org.plushy.factoryapi.repositories.MachineRepository;
import org.plushy.factoryapi.repositories.ParameterRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;

import java.io.File;

import com.fasterxml.jackson.databind.MappingIterator;

@Configuration
@Slf4j
class InitDatabase {
	@Bean
	CommandLineRunner preloadDatabase(MachineRepository machineRepository, ParameterRepository parameterRepository) {
		return args -> {
            String machineFileName = "machines.csv";
            loadMachines(machineRepository, machineFileName);
            String parametersFileName = "parameters.csv";
            loadParameters(parameterRepository, parametersFileName);
        };
    }

    void loadMachines(MachineRepository machineRepository, String fileName){
        try {
            File machineDbFile = new ClassPathResource(fileName).getFile(); 
            CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
            CsvMapper mapper = new CsvMapper();
            MappingIterator<Machine> readValues =
                mapper.readerFor(Machine.class).with(bootstrapSchema).readValues(machineDbFile);
            // for( readValues )
            while(readValues.hasNext()){
                Machine machine = readValues.next();
                log.info("Preloading: " + machineRepository.save(machine));
            }
        } catch (Exception e) {
            log.error("Error occurred while loading object list from file: " + fileName + e.toString());
        }
    };

    void loadParameters(ParameterRepository parameterRepository, String fileName){
        try {
            File parameterDbFile = new ClassPathResource(fileName).getFile(); 
            CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
            CsvMapper mapper = new CsvMapper();
            // mapper.enable(CsvParser.Feature.IGNORE_TRAILING_UNMAPPABLE);

            MappingIterator<Parameter> readValues =
                mapper.readerFor(Parameter.class).with(bootstrapSchema).readValues(parameterDbFile);
            // for( readValues )
            while(readValues.hasNext()){
                Parameter parameter = readValues.next();
                log.info("Preloading: " + parameterRepository.save(parameter));
            }
        } catch (Exception e) {
            log.error("Error occurred while loading object list from file: " + fileName + e.toString());
        }
    };
}
