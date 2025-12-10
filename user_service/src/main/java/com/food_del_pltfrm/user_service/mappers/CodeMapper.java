package com.food_del_pltfrm.user_service.mappers;



import com.food_del_pltfrm.user_service.dtos.CodeDTO;
import com.food_del_pltfrm.user_service.dtos.CodeEvent;
import com.food_del_pltfrm.user_service.entities.VerificationCode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface CodeMapper {
    @Mapping(target = "code", source = "code")
    CodeEvent toCodeDto(VerificationCode code);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", source = "code")
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.util.time.LocalTime.now())")
    @Mapping(target = "expiresAt", expression = "java(java.util.time.LocalTime.now().plusHours(3))")
    VerificationCode toCode(CodeDTO codeDTO);

}
