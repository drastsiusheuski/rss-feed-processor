CREATE TABLE rss_item
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    title          VARCHAR(100)             NOT NULL,
    description    VARCHAR(1000)            NOT NULL,
    uri            VARCHAR(500)             NOT NULL,
    published_date TIMESTAMP WITH TIME ZONE NOT NULL,
    inserted_date  TIMESTAMP WITH TIME ZONE NOT NULL,
    UNIQUE (uri)
);

CREATE INDEX idx_published_date ON rss_item(published_date);