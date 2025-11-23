-- Case table
create table if not exists case_t
(
    case_id            bigserial primary key,
    case_type          varchar(255) not null,
    case_status        varchar(255) not null,
    case_number        varchar(255) not null,
    description        varchar(255) not null,
    created_by         varchar(255) not null,
    created_date       timestamp    not null,
    last_modified_date timestamp,
    assigned_unit      varchar(255),
    assigned_user      varchar(255),
    sensitive          boolean      not null
);

-- Party table
create table if not exists party_t
(
    party_id           bigserial primary key,
    ssn                varchar(255) not null,
    first_name         varchar(255),
    last_name          varchar(255) not null,
    address_line1      varchar(255),
    address_line2      varchar(255),
    address_line3      varchar(255),
    postal_code        varchar(255) not null,
    city               varchar(255) not null,
    phone_number       varchar(255) not null,
    email              varchar(255) not null,
    created_date       timestamp    not null,
    last_modified_date timestamp
);

-- Party role relation
create table if not exists case_party_role_t
(
    id       bigserial primary key,
    case_id  bigint       not null,
    party_id bigint       not null,
    role     varchar(255) not null
);

-- Comment table
create table if not exists comment_t
(
    comment_id   bigserial primary key,
    comment      text         not null,
    created_by   varchar(255) not null,
    created_date timestamp    not null,
    case_id      bigint       not null
);

-- Case number sequence
create sequence case_number_sequence start 1001;

