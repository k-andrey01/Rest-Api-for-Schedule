CREATE TABLE "city"(
                       "id" SERIAL PRIMARY KEY,
                       "name" TEXT DEFAULT '' NOT NULL);

CREATE TABLE "type"(
                       "id" SERIAL PRIMARY KEY,
                       "name" TEXT DEFAULT '' NOT NULL);

CREATE TABLE "language"(
                           "id" SERIAL PRIMARY KEY,
                           "name" TEXT DEFAULT '' NOT NULL);

CREATE TABLE "place"(
                        "id" BIGSERIAL PRIMARY KEY,
                        "city_id" INTEGER REFERENCES "city"("id"),
                        "name" TEXT DEFAULT '' NOT NULL,
                        "street" TEXT DEFAULT '' NOT NULL,
                        "house" TEXT DEFAULT '' NOT NULL);

CREATE TABLE "performance"(
                              "id" BIGSERIAL PRIMARY KEY,
                              "type_id" INTEGER REFERENCES "type"("id"),
                              "language_id" INTEGER REFERENCES "language"("id"),
                              "name" TEXT DEFAULT '' NOT NULL);

CREATE TABLE "schedule"(
                           "id" BIGSERIAL PRIMARY KEY,
                           "place_id" INTEGER REFERENCES "place"("id"),
                           "performance_id" INTEGER REFERENCES "performance"("id"),
                           "date_time" TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW() NOT NULL)