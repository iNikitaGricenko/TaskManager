package com.wolfhack.todo.model;

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
	@Column(name = "id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private EntityUser user;

	@ManyToOne
	@JoinColumn(name = "task", referencedColumnName = "id")
	private EntityTask task;

	@ManyToOne
	@JoinColumn(name = "created_by_id", nullable = false)
	private EntityUser createdBy;

	@ManyToMany
	@JoinColumn(name = "updated_by_id")
	@ToString.Exclude
	private List<EntityUser> updatedBy = new LinkedList<>();

	@Column(name = "title", unique = true, nullable = false)
	private String title;

	@Column(name = "description")
	private String description;

	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private ActivityStatus status;

	@Column(name = "hours", nullable = false)
	private float hours;

	@Column(name = "created_at", nullable = false)
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
