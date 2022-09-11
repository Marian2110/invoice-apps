package ro.fastrackit.paymentservice.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ro.fastrackit.paymentservice.model.entity.PaymentEntity;
import ro.fasttrackit.payments.client.dto.Payment;

import java.util.List;

@Mapper
public interface PaymentMapper {
    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);
    List<Payment> toApi(List<PaymentEntity> students);

    Payment toApi(PaymentEntity student);

    PaymentEntity toEntity(Payment student);

}
