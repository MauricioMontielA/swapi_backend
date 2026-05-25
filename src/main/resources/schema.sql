PRAGMA foreign_keys = ON;

CREATE TABLE IF NOT EXISTS sp_user (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    email VARCHAR NOT NULL UNIQUE,
    username VARCHAR,
    password_hash VARCHAR,
    auth_provider VARCHAR NOT NULL, 
    provider_user_id VARCHAR,
    profile_image_url VARCHAR,
    created_at TIMESTAMP,
    modified_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS sp_collection (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    code VARCHAR,
    name VARCHAR,
    type VARCHAR,
    created_at TIMESTAMP,
    modified_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS sp_collectibleItem (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    number VARCHAR,
    code VARCHAR,
    name VARCHAR,
    image_url VARCHAR,
    rarity VARCHAR,
    collection_id INTEGER,
    created_at TIMESTAMP,
    modified_at TIMESTAMP,

    FOREIGN KEY (collection_id) REFERENCES sp_collection(id)
);

CREATE TABLE IF NOT EXISTS sp_attributeDefinition (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    collection_id INTEGER,
    key VARCHAR,
    label VARCHAR,
    data_type VARCHAR,
    is_filterable BOOLEAN,
    is_required BOOLEAN,
    created_at TIMESTAMP,
    modified_at TIMESTAMP,

    FOREIGN KEY (collection_id) REFERENCES sp_collection(id)
);

CREATE TABLE IF NOT EXISTS sp_collectibleItemAttribute (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    collectible_item_id INTEGER,
    attribute_definition_id INTEGER,
    value_text VARCHAR,
    value_number NUMERIC,
    value_boolean BOOLEAN,
    value_date TIMESTAMP,
    created_at TIMESTAMP,
    modified_at TIMESTAMP,

    FOREIGN KEY (collectible_item_id ) REFERENCES sp_collectibleItem(id),
    FOREIGN KEY (attribute_definition_id) REFERENCES sp_attributeDefinition(id)
);

CREATE TABLE IF NOT EXISTS sp_userCollectible (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER,
    collectible_item_id  INTEGER,
    quantity INTEGER,
    notes VARCHAR,
    is_for_trade BOOLEAN,
    is_for_sale BOOLEAN,
    created_at TIMESTAMP,
    modified_at TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES sp_user(id),
    FOREIGN KEY (collectible_item_id ) REFERENCES sp_collectibleItem(id)
);

CREATE TABLE IF NOT EXISTS sp_listing (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_collectible_id INTEGER,
    listing_type VARCHAR,
    quantity INTEGER,
    price_amount NUMERIC,
    price_currency VARCHAR,
    description VARCHAR,
    status VARCHAR,
    created_at TIMESTAMP,
    modified_at TIMESTAMP,

    FOREIGN KEY (user_collectible_id) REFERENCES sp_userCollectible(id)
);

CREATE TABLE IF NOT EXISTS sp_trade (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    status VARCHAR,
    created_at TIMESTAMP,
    modified_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS sp_tradeParticipant (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    trade_id INTEGER,
    user_id INTEGER,
    status VARCHAR,
    created_at TIMESTAMP,
    modified_at TIMESTAMP,

    FOREIGN KEY (trade_id) REFERENCES sp_trade(id),
    FOREIGN KEY (user_id) REFERENCES sp_user(id)
);

CREATE TABLE IF NOT EXISTS sp_tradeItem (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    trade_id INTEGER,
    from_participant_id INTEGER,
    to_participant_id INTEGER,
    user_collectible_id INTEGER,
    quantity INTEGER,
    created_at TIMESTAMP,
    modified_at TIMESTAMP,

    FOREIGN KEY (trade_id) REFERENCES sp_trade(id),
    FOREIGN KEY (from_participant_id) REFERENCES sp_tradeParticipant(id),
    FOREIGN KEY (to_participant_id) REFERENCES sp_tradeParticipant(id),
    FOREIGN KEY (user_collectible_id) REFERENCES sp_userCollectible(id)
);

CREATE TABLE IF NOT EXISTS sp_refreshToken (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    token VARCHAR(500) NOT NULL UNIQUE,
    user_id INTEGER NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    revoked BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES sp_user(id)
);