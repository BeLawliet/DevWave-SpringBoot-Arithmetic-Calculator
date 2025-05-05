package arithmetic.calculator.api.config;

import arithmetic.calculator.api.persistence.model.Operation;
import arithmetic.calculator.api.presentation.dto.OperationResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        mapper.typeMap(Operation.class, OperationResponseDTO.class)
              .addMapping(src -> src.getUser().getId(), OperationResponseDTO::setUserId);

        return mapper;
    }
}
