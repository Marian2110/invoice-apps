package ro.fastrackit.paymentservice.exception.custom;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Builder
public class ResourceNotFoundException extends RuntimeException {
    private final String id;
    private final String entityName;

    public ResourceNotFoundException(String id, String entityName) {
        super(entityName + " with id " + id + " not found");
        this.entityName = entityName;
        this.id = id;
    }

    public static <T> ResourceNotFoundException forEntity(Class<T> entity, String id) {
        String entityName = entity.getSimpleName();
        return createException(id, "Could not find " + entityName + " with id " + id, entityName);
    }

    private static ResourceNotFoundException createException(String id, String errorMessage, String entityName) {
        ResourceNotFoundException resourceNotFoundException = ResourceNotFoundException.builder().entityName(entityName).id(id).build();
        log.error(errorMessage, id, resourceNotFoundException);
        return resourceNotFoundException;
    }
}
