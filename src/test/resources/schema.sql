CREATE TABLE IF NOT EXISTS posts (
    id         INTEGER GENERATED BY DEFAULT AS IDENTITY,
    title      VARCHAR(255) NOT NULL,
    message    TEXT NOT NULL,
    image      BYTEA,
    image_name VARCHAR(255),
    likes      INTEGER,
    changed    TIMESTAMP WITH TIME ZONE,
    CONSTRAINT pk_post PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS comments
(
    id         INTEGER GENERATED BY DEFAULT AS IDENTITY,
    message    TEXT,
    post_id    INTEGER NOT NULL,
    changed    TIMESTAMP WITH TIME ZONE,
    CONSTRAINT pk_comment PRIMARY KEY (id),
    CONSTRAINT fk_comment_post FOREIGN KEY (post_id) REFERENCES posts (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS tags
(
    id         INTEGER GENERATED BY DEFAULT AS IDENTITY,
    name       VARCHAR(255) NOT NULL,
    CONSTRAINT pk_tag PRIMARY KEY (id),
    CONSTRAINT uq_tag UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS posts_tags
(
    post_id    INTEGER NOT NULL,
    tag_id     INTEGER NOT NULL,
    CONSTRAINT uk_post_tag UNIQUE (post_id, tag_id),
    CONSTRAINT fk_post FOREIGN KEY (post_id) REFERENCES posts (id) ON DELETE CASCADE,
    CONSTRAINT fk_tag FOREIGN KEY (tag_id) REFERENCES tags (id) ON DELETE CASCADE
);