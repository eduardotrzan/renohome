/**
 * Schema creation for RenoHome Contractor
 *
 */

CREATE SEQUENCE contractor_id_seq
    INCREMENT 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1;
END;

CREATE TABLE "contractor" (
    "id"                         BIGINT DEFAULT nextval('contractor_id_seq' :: REGCLASS) NOT NULL,
    "uuid"                       UUID                                                        NOT NULL DEFAULT uuid_generate_v4(),
    "create_date"                TIMESTAMP WITH TIME ZONE                                    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "update_date"                TIMESTAMP WITH TIME ZONE                                    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "version"                    BIGINT                                                      NOT NULL,
    "name"                       VARCHAR(200)                                                NOT NULL,
    "phone"                      VARCHAR(200)                                                NOT NULL,
    "cost"                       DECIMAL(24, 12)                                             NOT NULL,
    "service_type"               VARCHAR(200)                                                NOT NULL,

    PRIMARY KEY ("id")
);
END;

CREATE UNIQUE INDEX "contractor_idx01"
    ON "contractor" ("uuid");

CREATE INDEX "contractor_idx02"
    ON "contractor" ("cost");

CREATE UNIQUE INDEX "contractor_idx03"
    ON "contractor" ("name", "service_type");
