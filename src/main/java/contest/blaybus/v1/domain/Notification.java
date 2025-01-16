package contest.blaybus.v1.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "notification")
@NoArgsConstructor
public class Notification {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private NotificationCategory category;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Notification(String title, String content, NotificationCategory category,LocalDateTime date, Member member) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.category = category;
        this.member = member;

    }

    public void setMember(Member member) {
        this.member = member;
    }
}
