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

import java.io.File;

import com.fasterxml.jackson.databind.MappingIterator;

@Configuration
@Slf4j
public class InitDatabase {
    @Bean
    CommandLineRunner preloadDatabase(final MachineRepository machineRepository,
            final ParameterRepository parameterRepository) {
        return args -> {
            final String machineFileName = "machines.csv";
            loadMachines(machineRepository, machineFileName);
            final String parametersFileName = "parameters.csv";
            loadParameters(parameterRepository, parametersFileName, machineRepository);
        };
    }

    public static void loadMachines(final MachineRepository machineRepository, final String fileName) {
        try {
            final File machineDbFile = new ClassPathResource(fileName).getFile();
            final CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
            final CsvMapper mapper = new CsvMapper();
            final MappingIterator<Machine> readValues = mapper.readerFor(Machine.class).with(bootstrapSchema)
                    .readValues(machineDbFile);
            // for( readValues )
            while (readValues.hasNext()) {
                final Machine machine = readValues.next();
                log.info("Preloading: " + machineRepository.save(machine));
            }
        } catch (final Exception e) {
            log.error("Error occurred while loading object list from file: " + fileName + e.toString());
        }
    };

    public static void loadParameters(final ParameterRepository parameterRepository, final String fileName,
            final MachineRepository machineRepo) {
        try {
            final File parameterDbFile = new ClassPathResource(fileName).getFile();
            final CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
            final CsvMapper mapper = new CsvMapper();
            // mapper.enable(CsvParser.Feature.IGNORE_TRAILING_UNMAPPABLE);

            final MappingIterator<Parameter> readValues = mapper.readerFor(Parameter.class).with(bootstrapSchema)
                    .readValues(parameterDbFile);
            while (readValues.hasNext()) {
                final Parameter parameter = readValues.next();
                System.out.println(parameter);
                final Machine machineForParam = machineRepo.getOne(parameter.getMachineKey());
                parameter.setMachine(machineForParam);
                System.out.println(parameter);
                log.info("Preloading: " + parameterRepository.save(parameter));
            }
        } catch (final Exception e) {
            log.error("Error occurred while loading object list from file: " + fileName + e.toString());
        }
    };
}
