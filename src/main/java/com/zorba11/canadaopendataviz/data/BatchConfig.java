package com.zorba11.canadaopendataviz.data;

import com.zorba11.canadaopendataviz.model.SoldHouse;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private final String[] FIELD_NAMES = new String[] {
            "id", "address", "area_name", "price", "lat", "lng"
    };

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;



    @Bean
    public FlatFileItemReader<SoldHouseInput> reader() {
        return new FlatFileItemReaderBuilder<SoldHouseInput>()
                .name("HouseSaleDataReader")
                .resource(new ClassPathResource("ont-house-sales-1.csv"))
                .delimited()
                .names(FIELD_NAMES)
                .fieldSetMapper(new BeanWrapperFieldSetMapper<SoldHouseInput>() {{
                    setTargetType(SoldHouseInput.class);
                }})
                .build();
    }

    @Bean
    public HouseSalesDataProcessor processor() {
        return new HouseSalesDataProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<SoldHouse> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<SoldHouse>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO houses (id, address, area_name, price, lat, lng)"
                        + "VALUES (:id, :address, :areaName, :price, :lat, :lng)")
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<SoldHouse> writer) {
        return stepBuilderFactory.get("step1")
                .<SoldHouseInput, SoldHouse> chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }

}
