package helloJpa2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Embeddable
public class WorkPeriod {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
