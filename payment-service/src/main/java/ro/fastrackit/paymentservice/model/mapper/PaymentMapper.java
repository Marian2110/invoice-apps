package ro.fastrackit.paymentservice.model.mapper;

import org.mapstruct.Mapper;
import ro.fastrackit.paymentservice.model.entity.PaymentEntity;
import ro.fasttrackit.payments.client.dto.Payment;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    List<Payment> toApi(List<PaymentEntity> students);

    Payment toApi(PaymentEntity student);

    PaymentEntity toEntity(Payment student);

}
