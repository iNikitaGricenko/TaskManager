create table public."`user"
(
    online            boolean      not null,
    registration_date date         not null,
    id                bigserial
        primary key,
    last_login        timestamp(6) not null,
    updated_by_id     bigint,
    email             varchar(255) not null
        unique,
    first_name        varchar(255) not null,
    intro             varchar(255),
    last_name         varchar(255) not null,
    password          varchar(255) not null,
    profile           varchar(255),
    role              varchar(255) not null
        constraint "`user_role_check"
            check ((role)::text = ANY
                   ((ARRAY ['DEFAULT'::character varying, 'JUNIOR'::character varying, 'MIDDLE'::character varying, 'SENIOR'::character varying, 'TEAM_LEAD'::character varying, 'ADMIN'::character varying])::text[])),
    username          varchar(255) not null
        unique
);

alter table public."`user"
    owner to root;

create table public.tag
(
    id    bigserial
        primary key,
    slug  varchar(255),
    title varchar(255) not null
        unique
);

alter table public.tag
    owner to root;

create table public.task
(
    created_at         date         not null,
    hours              real         not null,
    updated_at         date,
    actual_end_date    timestamp(6),
    actual_start_date  timestamp(6),
    created_by_id      bigint
        constraint fk7fg2m2ioa082rela5ljq90kpb
            references public."`user",
    id                 bigserial
        primary key,
    planned_end_date   timestamp(6),
    planned_start_date timestamp(6),
    user_id            bigint
        constraint fko2yhjrwrpiw9u1xpswcry7rgn
            references public."`user",
    description        varchar(255),
    status             varchar(255) not null
        constraint task_status_check
            check ((status)::text = ANY
                   ((ARRAY ['STARTING'::character varying, 'IN_PROGRESS'::character varying, 'COMPLETED'::character varying, 'PAUSED'::character varying, 'CANCELLED'::character varying])::text[])),
    title              varchar(255)
        unique
);

alter table public.task
    owner to root;

alter table public."`user"
    add constraint fkpnqw5vvnc41uncvwr76s3admj
        foreign key (updated_by_id) references public.task;

create table public.activity
(
    created_at         date         not null,
    hours              real         not null,
    updated_at         date,
    actual_end_date    timestamp(6),
    actual_start_date  timestamp(6),
    created_by_id      bigint
        constraint fktqfrn46tesv92ghus55dma1it
            references public."`user",
    id                 bigserial
        primary key,
    planned_end_date   timestamp(6),
    planned_start_date timestamp(6),
    task               bigint
        constraint fkqrdu5n0p1q7snrd4slejnxbb0
            references public.task,
    user_id            bigint
        constraint fkalyjvddls2y1g6pebuc8cpl3n
            references public."`user",
    description        varchar(255),
    status             varchar(255) not null
        constraint activity_status_check
            check ((status)::text = ANY
                   ((ARRAY ['STARTED'::character varying, 'DONE'::character varying, 'OPEN'::character varying, 'CANCELLED'::character varying])::text[])),
    title              varchar(255) not null
        unique
);

alter table public.activity
    owner to root;

create table public.activity_updated_by
(
    activity_id   bigint not null
        constraint fkha8mb0r1hqy52d158kwx62snx
            references public.activity,
    updated_by_id bigint not null
        constraint fksqg73276ir3towx4ay14jxmch
            references public."`user"
);

alter table public.activity_updated_by
    owner to root;

create table public.comment
(
    activity    bigint
        constraint fkraqdqfcg5j5daco4ma3iv2v8l
            references public.activity,
    creadted_at timestamp(6) not null,
    id          bigserial
        primary key,
    task        bigint
        constraint fkbs7cjy74v3fsbxc9gyaxkc6ov
            references public.task,
    updated_at  timestamp(6),
    user_id     bigint
        constraint fkgsm19p6k647twxn8298r4jvou
            references public."`user",
    content     varchar(255) not null,
    title       varchar(255) not null
);

alter table public.comment
    owner to root;

create table public.task_meta
(
    id      bigserial
        primary key,
    task    bigint
        unique
        constraint fkf01c1kj4qbs5o4tf54mbwhd25
            references public.task,
    content varchar(255),
    key     varchar(255)
        unique
);

alter table public.task_meta
    owner to root;

create table public.task_tag
(
    id   bigserial
        primary key,
    tag  bigint not null
        unique
        constraint fk3kp64dk6r0xo68f0japq0ld9k
            references public.tag,
    task bigint not null
        unique
        constraint fkpgc8qgi5caj5bq6q3it52akrr
            references public.task
);

alter table public.task_tag
    owner to root;