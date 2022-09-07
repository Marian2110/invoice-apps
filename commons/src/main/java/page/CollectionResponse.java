package page;

import lombok.Builder;

import java.util.List;

@Builder
public record CollectionResponse<T>(
        List<T> items,
        PageInfo pageInfo
) {
}
