package com.wolfhack.todo.model.entity;

import com.wolfhack.todo.model.enums.ActivityStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity(name = "activity")
public class EntityActivity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany
	@ToString.Exclude
	private Set<EntityUser> user = new HashSet<>();

	@ManyToOne
	private EntityTask task;

	@OneToOne
	private EntityUser createdBy;

	@OneToMany
	@ToString.Exclude
	private List<EntityUser> updatedBy = new LinkedList<>();

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

	@Column(name = "status")
	private ActivityStatus status;

	@Column(name = "hours")
	private float hours;

	@Column(name = "created_at")
	private LocalDate createdAt;

	@Column(name = "updated_at")
	private LocalDate updatedAt;

	@Column(name = "planned_start_date")
	private LocalDateTime plannedStartDate;

	@Column(name = "planned_end_date")
	private LocalDateTime plannedEndDate;

	@Column(name = "actual_start_date")
	private LocalDateTime actualStartDate;

	@Column(name = "actual_end_date")
	private LocalDateTime actualEndDate;

	@Column(name = "content")
	private String content;

	@Override
	public final boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null) {
			return false;
		}
		Class<?> oEffectiveClass = object instanceof HibernateProxy ? ((HibernateProxy) object).getHibernateLazyInitializer().getPersistentClass() : object.getClass();
		Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
		if (thisEffectiveClass != oEffectiveClass) {
			return false;
		}
		EntityActivity that = (EntityActivity) object;
		return getId() != null && Objects.equals(getId(), that.getId());
	}

	@Override
	public final int hashCode() {
		return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
	}
}
