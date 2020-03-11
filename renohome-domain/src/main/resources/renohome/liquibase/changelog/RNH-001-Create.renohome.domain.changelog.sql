/**
 * Schema creation for RenoHome
 *
 */



------------------------------------------------------------------------------------------------------------------------


CREATE SEQUENCE home_id_seq
    INCREMENT 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1;
END;

CREATE TABLE "home" (
    "id"                         BIGINT DEFAULT nextval('home_id_seq' :: REGCLASS) NOT NULL,
    "uuid"                       UUID                                                NOT NULL DEFAULT uuid_generate_v4(),
    "create_date"                TIMESTAMP WITH TIME ZONE                            NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "update_date"                TIMESTAMP WITH TIME ZONE                            NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "version"                    BIGINT                                              NOT NULL,
    "owner"                      VARCHAR(200)                                        NOT NULL,
    "address"                    VARCHAR(70)                                         NOT NULL,

    PRIMARY KEY ("id")
);
END;

CREATE UNIQUE INDEX "home_idx01"
    ON "home" ("uuid");

CREATE INDEX "home_idx02"
    ON "home" ("owner");


------------------------------------------------------------------------------------------------------------------------


CREATE SEQUENCE service_id_seq
    INCREMENT 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1;
END;

CREATE TABLE "service" (
    "id"                          BIGINT DEFAULT nextval('service_id_seq' :: REGCLASS) NOT NULL,
    "uuid"                        UUID                                                        NOT NULL DEFAULT uuid_generate_v4(),
    "create_date"                 TIMESTAMP WITH TIME ZONE                                    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "update_date"                 TIMESTAMP WITH TIME ZONE                                    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "version"                     BIGINT                                                      NOT NULL,
    "type"                        VARCHAR(200)                                                NOT NULL, -- CHANGE_SINK, CHANGE_FLOORING, PAINT ...

    PRIMARY KEY ("id")
);
END;

CREATE UNIQUE INDEX "service_idx01"
    ON "service" ("uuid");

CREATE UNIQUE INDEX "service_idx02"
    ON "service" ("type");


------------------------------------------------------------------------------------------------------------------------


CREATE SEQUENCE home_service_request_id_seq
    INCREMENT 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1;
END;

CREATE TABLE "home_service_request" (
    "id"                          BIGINT DEFAULT nextval('home_service_request_id_seq' :: REGCLASS) NOT NULL,
    "uuid"                        UUID                                                        NOT NULL DEFAULT uuid_generate_v4(),
    "create_date"                 TIMESTAMP WITH TIME ZONE                                    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "update_date"                 TIMESTAMP WITH TIME ZONE                                    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "version"                     BIGINT                                                      NOT NULL,
    "home_id"                     BIGINT                                                      NOT NULL,
    "service_id"                  BIGINT                                                      NOT NULL,
    "contractor_uuid"             UUID                                                        NULL,
    "schedule_date"               TIMESTAMP WITH TIME ZONE                                    NOT NULL,
    "budget"                      DECIMAL(24, 12)                                             NOT NULL,

    PRIMARY KEY ("id")
);
END;

CREATE UNIQUE INDEX "home_service_request_idx01"
    ON "home_service_request" ("uuid");

CREATE UNIQUE INDEX "home_service_request_idx02"
    ON "home_service_request" ("home_id", "service_id", "contractor_uuid", "schedule_date")
WHERE "contractor_uuid" IS NOT NULL;

CREATE INDEX "home_service_request_idx03"
    ON "home_service_request" ("home_id", "service_id");
