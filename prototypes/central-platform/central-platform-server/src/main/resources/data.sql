-- Insert breweries
INSERT INTO organization (id, name, organization_type)
VALUES
    ('7c697a6f-8d4a-4c8b-8e21-7e57fc07316a','Grolsch','BREWERY'),
    ('eaa663d1-4eb7-4a9f-9a90-d61c0d82a856', 'Heineken', 'BREWERY'),
    ('1b92a522-e99d-42e7-925d-7a065d6c9d50', 'Hertog Jan', 'BREWERY'),
    ('eec8b7a2-20a2-47da-8c9a-bac8d68a3f5a', 'Kordaat', 'BREWERY');

-- Insert supermarkets
INSERT INTO organization (id, name, organization_type)
VALUES
    ('3e4bdc4e-51d2-4e82-a05a-93ac3e3a7861', 'Lidl', 'SUPERMARKET'),
    ('9c24c37b-6a5b-4c5a-8b4e-0b3a5a8f385d', 'Aldi', 'SUPERMARKET'),
    ('8318c3b0-590c-4845-8cb9-d30d2f344edb', 'Albert Heijn', 'SUPERMARKET'),
    ('d1408c98-878d-4d2a-b5c5-370fea22d312', 'Hoogvliet', 'SUPERMARKET'),
    ('c84e07cc-0d42-44a5-8363-afde90302e9a', 'Jumbo', 'SUPERMARKET');

-- Insert organization access entities for breweries
INSERT INTO organization_access (id, organization_id)
VALUES
    ('7c697a6f-8d4a-4c8b-8e21-7e57fc07316b', '7c697a6f-8d4a-4c8b-8e21-7e57fc07316a'), -- Grolsch
    ('eaa663d1-4eb7-4a9f-9a90-d61c0d82a857', 'eaa663d1-4eb7-4a9f-9a90-d61c0d82a856'), -- Heineken
    ('1b92a522-e99d-42e7-925d-7a065d6c9d51', '1b92a522-e99d-42e7-925d-7a065d6c9d50'), -- Hertog Jan
    ('eec8b7a2-20a2-47da-8c9a-bac8d68a3f5b', 'eec8b7a2-20a2-47da-8c9a-bac8d68a3f5a'); -- Kordaat

-- Insert organization access entities for supermarkets
INSERT INTO organization_access (id, organization_id)
VALUES
    ('3e4bdc4e-51d2-4e82-a05a-93ac3e3a7862', '3e4bdc4e-51d2-4e82-a05a-93ac3e3a7861'), -- Lidl
    ('9c24c37b-6a5b-4c5a-8b4e-0b3a5a8f385e', '9c24c37b-6a5b-4c5a-8b4e-0b3a5a8f385d'), -- Aldi
    ('8318c3b0-590c-4845-8cb9-d30d2f344edc', '8318c3b0-590c-4845-8cb9-d30d2f344edb'), -- Albert Heijn
    ('d1408c98-878d-4d2a-b5c5-370fea22d313', 'd1408c98-878d-4d2a-b5c5-370fea22d312'), -- Hoogvliet
    ('c84e07cc-0d42-44a5-8363-afde90302ea0', 'c84e07cc-0d42-44a5-8363-afde90302e9a'); -- Jumbo

INSERT INTO organization_access_allowed_organizations (organization_access_id, organization_id)
VALUES
    ('7c697a6f-8d4a-4c8b-8e21-7e57fc07316b', '3e4bdc4e-51d2-4e82-a05a-93ac3e3a7861'), -- Grolsch - Lidl
    ('7c697a6f-8d4a-4c8b-8e21-7e57fc07316b', '9c24c37b-6a5b-4c5a-8b4e-0b3a5a8f385d'), -- Grolsch - Aldi
    ('7c697a6f-8d4a-4c8b-8e21-7e57fc07316b', '8318c3b0-590c-4845-8cb9-d30d2f344edb'),  -- Grolsch - Albert Heijn
    ('7c697a6f-8d4a-4c8b-8e21-7e57fc07316b', 'd1408c98-878d-4d2a-b5c5-370fea22d312'),  -- Grolsch - Hoogvliet
    ('7c697a6f-8d4a-4c8b-8e21-7e57fc07316b', 'c84e07cc-0d42-44a5-8363-afde90302e9a'),  -- Grolsch - Jumbo

    ('3e4bdc4e-51d2-4e82-a05a-93ac3e3a7862', '7c697a6f-8d4a-4c8b-8e21-7e57fc07316a'), -- Lidl - Grolsch
    ('3e4bdc4e-51d2-4e82-a05a-93ac3e3a7862', 'eaa663d1-4eb7-4a9f-9a90-d61c0d82a856'); -- Lidl - Heineken
-- Insert locations for breweries (2 locations each)
INSERT INTO location (id, organization_id, name, address, latitude, longitude)
VALUES
    -- Brewery 1: Heineken
    ('eaa663d1-4eb7-4a9f-9a90-d61c0d82a857', 'eaa663d1-4eb7-4a9f-9a90-d61c0d82a856', 'Heineken Brewery 1', 'Heineken Brewery Address 1', '51.820054', '4.481172'),
    ('eaa663d1-4eb7-4a9f-9a90-d61c0d82a858', 'eaa663d1-4eb7-4a9f-9a90-d61c0d82a856', 'Heineken Brewery 2', 'Heineken Brewery Address 2', '52.479189', '4.899431'),

    -- Brewery 2: Hertog Jan
    ('eaa663d1-4eb7-4a9f-9a90-d61c0d82a859', '1b92a522-e99d-42e7-925d-7a065d6c9d50', 'Hertog Jan Brewery 1', 'Hertog Jan Brewery Address 1', '52.090737', '5.621420'),
    ('eaa663d1-4eb7-4a9f-9a90-d61c0d82a85a', '1b92a522-e99d-42e7-925d-7a065d6c9d50', 'Hertog Jan Brewery 2', 'Hertog Jan Brewery Address 2', '51.378670', '5.440262'),

    -- Brewery 3: Kordaat
    ('eaa663d1-4eb7-4a9f-9a90-d61c0d82a85b', 'eec8b7a2-20a2-47da-8c9a-bac8d68a3f5a', 'Kordaat Brewery 1', 'Kordaat Brewery Address 1', '51.560131', '4.874955'),
    ('eaa663d1-4eb7-4a9f-9a90-d61c0d82a85c', 'eec8b7a2-20a2-47da-8c9a-bac8d68a3f5a', 'Kordaat Brewery 2', 'Kordaat Brewery Address 2', '52.271514', '4.503863'),

    -- Brewery 4: Grolsch
    ('eaa663d1-4eb7-4a9f-9a90-d61c0d82a85d', '7c697a6f-8d4a-4c8b-8e21-7e57fc07316a', 'Grolsch Brewery 1', 'Grolsch Brewery Address 1', '52.435474', '6.661176'),
    ('eaa663d1-4eb7-4a9f-9a90-d61c0d82a85e', '7c697a6f-8d4a-4c8b-8e21-7e57fc07316a', 'Grolsch Brewery 2', 'Grolsch Brewery Address 2', '52.331646', '6.696802');

-- Insert locations for supermarkets (5 locations each)
INSERT INTO location (id, organization_id, name, address, latitude, longitude)
VALUES
    -- Supermarket 1: Lidl
    ('eaa663d1-4eb7-4a9f-9a90-d61c0d82a85f', '3e4bdc4e-51d2-4e82-a05a-93ac3e3a7861', 'Lidl Supermarket 1', 'Lidl Supermarket Address 1', '51.719439', '5.277911'),
    ('eaa663d1-4eb7-4a9f-9a90-d61c0d82a860', '3e4bdc4e-51d2-4e82-a05a-93ac3e3a7861', 'Lidl Supermarket 2', 'Lidl Supermarket Address 2', '52.193168', '5.171490'),
    ('eaa663d1-4eb7-4a9f-9a90-d61c0d82a861', '3e4bdc4e-51d2-4e82-a05a-93ac3e3a7861', 'Lidl Supermarket 3', 'Lidl Supermarket Address 3', '51.462900', '5.956246'),
    ('eaa663d1-4eb7-4a9f-9a90-d61c0d82a862', '3e4bdc4e-51d2-4e82-a05a-93ac3e3a7861', 'Lidl Supermarket 4', 'Lidl Supermarket Address 4', '52.626750', '5.278769'),
    ('eaa663d1-4eb7-4a9f-9a90-d61c0d82a863', '3e4bdc4e-51d2-4e82-a05a-93ac3e3a7861', 'Lidl Supermarket 5', 'Lidl Supermarket Address 5', '51.477077', '5.192023'),

    -- Supermarket 2: Aldi
    ('eaa663d1-4eb7-4a9f-9a90-d61c0d82a864', '9c24c37b-6a5b-4c5a-8b4e-0b3a5a8f385d', 'Aldi Supermarket 1', 'Aldi Supermarket Address 1', '51.560131', '4.874955'),
    ('eaa663d1-4eb7-4a9f-9a90-d61c0d82a865', '9c24c37b-6a5b-4c5a-8b4e-0b3a5a8f385d', 'Aldi Supermarket 2', 'Aldi Supermarket Address 2', '52.271514', '4.503863'),
    ('eaa663d1-4eb7-4a9f-9a90-d61c0d82a866', '9c24c37b-6a5b-4c5a-8b4e-0b3a5a8f385d', 'Aldi Supermarket 3', 'Aldi Supermarket Address 3', '51.525150', '4.776442'),
    ('eaa663d1-4eb7-4a9f-9a90-d61c0d82a867', '9c24c37b-6a5b-4c5a-8b4e-0b3a5a8f385d', 'Aldi Supermarket 4', 'Aldi Supermarket Address 4', '52.823367', '4.890799'),
    ('eaa663d1-4eb7-4a9f-9a90-d61c0d82a868', '9c24c37b-6a5b-4c5a-8b4e-0b3a5a8f385d', 'Aldi Supermarket 5', 'Aldi Supermarket Address 5', '51.677177', '4.530216'),

    -- Supermarket 3: Albert Heijn
    ('eaa663d1-4eb7-4a9f-9a90-d61c0d82a869', '8318c3b0-590c-4845-8cb9-d30d2f344edb', 'Albert Heijn Supermarket 1', 'Albert Heijn Supermarket Address 1', '51.679084', '4.586695'),
    ('eaa663d1-4eb7-4a9f-9a90-d61c0d82a86a', '8318c3b0-590c-4845-8cb9-d30d2f344edb', 'Albert Heijn Supermarket 2', 'Albert Heijn Supermarket Address 2', '52.591010', '4.723285'),
    ('eaa663d1-4eb7-4a9f-9a90-d61c0d82a86b', '8318c3b0-590c-4845-8cb9-d30d2f344edb', 'Albert Heijn Supermarket 3', 'Albert Heijn Supermarket Address 3', '51.413678', '4.794666'),
    ('eaa663d1-4eb7-4a9f-9a90-d61c0d82a86c', '8318c3b0-590c-4845-8cb9-d30d2f344edb', 'Albert Heijn Supermarket 4', 'Albert Heijn Supermarket Address 4', '51.619146', '4.577397'),
    ('eaa663d1-4eb7-4a9f-9a90-d61c0d82a86d', '8318c3b0-590c-4845-8cb9-d30d2f344edb', 'Albert Heijn Supermarket 5', 'Albert Heijn Supermarket Address 5', '52.266029', '4.878873'),

    -- Supermarket 4: Hoogvliet
    ('eaa663d1-4eb7-4a9f-9a90-d61c0d82a86e', 'd1408c98-878d-4d2a-b5c5-370fea22d312', 'Hoogvliet Supermarket 1', 'Hoogvliet Supermarket Address 1', '51.407871', '4.596234'),
    ('eaa663d1-4eb7-4a9f-9a90-d61c0d82a86f', 'd1408c98-878d-4d2a-b5c5-370fea22d312', 'Hoogvliet Supermarket 2', 'Hoogvliet Supermarket Address 2', '51.947613', '4.822633'),
    ('eaa663d1-4eb7-4a9f-9a90-d61c0d82a870', 'd1408c98-878d-4d2a-b5c5-370fea22d312', 'Hoogvliet Supermarket 3', 'Hoogvliet Supermarket Address 3', '51.496180', '4.527676'),
    ('eaa663d1-4eb7-4a9f-9a90-d61c0d82a871', 'd1408c98-878d-4d2a-b5c5-370fea22d312', 'Hoogvliet Supermarket 4', 'Hoogvliet Supermarket Address 4', '52.161728', '4.537832'),
    ('eaa663d1-4eb7-4a9f-9a90-d61c0d82a872', 'd1408c98-878d-4d2a-b5c5-370fea22d312', 'Hoogvliet Supermarket 5', 'Hoogvliet Supermarket Address 5', '52.329383', '4.840700'),

    -- Supermarket 5: Jumbo
    ('eaa663d1-4eb7-4a9f-9a90-d61c0d82a873', 'c84e07cc-0d42-44a5-8363-afde90302e9a', 'Jumbo Supermarket 1', 'Jumbo Supermarket Address 1', '51.920788', '4.696545'),
    ('eaa663d1-4eb7-4a9f-9a90-d61c0d82a874', 'c84e07cc-0d42-44a5-8363-afde90302e9a', 'Jumbo Supermarket 2', 'Jumbo Supermarket Address 2', '51.978423', '5.364862'),
    ('eaa663d1-4eb7-4a9f-9a90-d61c0d82a875', 'c84e07cc-0d42-44a5-8363-afde90302e9a', 'Jumbo Supermarket 3', 'Jumbo Supermarket Address 3', '52.026688', '5.246085'),
    ('eaa663d1-4eb7-4a9f-9a90-d61c0d82a876', 'c84e07cc-0d42-44a5-8363-afde90302e9a', 'Jumbo Supermarket 4', 'Jumbo Supermarket Address 4', '51.986324', '5.018581'),
    ('eaa663d1-4eb7-4a9f-9a90-d61c0d82a877', 'c84e07cc-0d42-44a5-8363-afde90302e9a', 'Jumbo Supermarket 5', 'Jumbo Supermarket Address 5', '52.058143', '4.417691');

-- Create a temporary table to store the allowed item types
CREATE TEMP TABLE allowed_item_types (item_type character varying);

-- Populate the allowed_item_types table with the allowed item types
INSERT INTO allowed_item_types (item_type)
VALUES
    ('GRLOSCH_PREMIUM_PILSNER'),
    ('GROLSCH_RADLER'),
    ('HERTOG_JAN_PILSNER'),
    ('HERTOG_JAN_GRAND_PILSNER'),
    ('HEINEKEN'),
    ('KORDAAT');

-- Loop through organizations (supermarkets/breweries)
DO '
DECLARE
    org_id uuid;
    location_id uuid;
    random_item_type character varying;
BEGIN
    FOR org_id IN (SELECT id FROM organization)
    LOOP
        -- Loop through locations for each organization
        FOR location_id IN (SELECT id FROM location WHERE organization_id = org_id)
        LOOP
            -- Create a temporary table to store used item types for this location
            CREATE TEMP TABLE used_item_types (item_type character varying);

            -- Generate a random number of unique item types for this location
            LOOP
                SELECT item_type
                FROM allowed_item_types
                WHERE item_type NOT IN (SELECT item_type FROM used_item_types)
                ORDER BY random()
                LIMIT 1
                INTO random_item_type;

                -- Exit the loop if there are no more unique item types for this location
                EXIT WHEN random_item_type IS NULL;

                -- Insert data into the inventory table for the location with a random quantity
                INSERT INTO inventory (quantity, id, location_id, item_type, capacity)
                VALUES
                    (floor(random() * 32)::integer,
                     gen_random_uuid(),
                     location_id,
                     random_item_type, 30);

                -- Store the used item type
                INSERT INTO used_item_types (item_type) VALUES (random_item_type);
            END LOOP;

            -- Clean up the temporary table
            DROP TABLE used_item_types;
        END LOOP;
    END LOOP;
END' LANGUAGE PLPGSQL;

-- Update inventory items related to Grolsch to be associated with Grolsch
UPDATE inventory
SET organization_id = '7c697a6f-8d4a-4c8b-8e21-7e57fc07316a'
WHERE item_type IN ('GRLOSCH_PREMIUM_PILSNER', 'GROLSCH_RADLER');

-- Update inventory items related to Heineken to be associated with Heineken
UPDATE inventory
SET organization_id = 'eaa663d1-4eb7-4a9f-9a90-d61c0d82a856'
WHERE item_type = 'HEINEKEN';

-- Update inventory items related to Hertog Jan to be associated with Hertog Jan
UPDATE inventory
SET organization_id = '1b92a522-e99d-42e7-925d-7a065d6c9d50'
WHERE item_type IN ('HERTOG_JAN_PILSNER', 'HERTOG_JAN_GRAND_PILSNER');

-- Update inventory items related to Kordaat to be associated with Kordaat
UPDATE inventory
SET organization_id = 'eec8b7a2-20a2-47da-8c9a-bac8d68a3f5a'
WHERE item_type = 'KORDAAT';
