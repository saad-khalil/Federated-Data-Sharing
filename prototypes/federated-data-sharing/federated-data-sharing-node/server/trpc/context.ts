import { inferAsyncReturnType } from '@trpc/server';
import Database from "better-sqlite3";

/**
 * Creates context for an incoming request
 * @link https://trpc.io/docs/context
 */
export const createContext = () => {
    const db = new Database('./federated.db')

    // 1
    const federated_node = db.prepare(
        `CREATE TABLE IF NOT EXISTS federated_node (
        node_id INTEGER PRIMARY KEY AUTOINCREMENT,
        name TEXT NOT NULL UNIQUE,
        security TEXT NOT NULL
    );`)
    // 2
    const organization = db.prepare(
        `CREATE TABLE IF NOT EXISTS organization (
        organization_id INTEGER PRIMARY KEY AUTOINCREMENT,
        name TEXT NOT NULL,
        secret_key TEXT NOT NULL,
        organization_type TEXT NOT NULL,
        security TEXT NOT NULL
    );`)
    // 3
    const organization_node = db.prepare(`
        CREATE TABLE IF NOT EXISTS organization_node (
        organization_node_id INTEGER PRIMARY KEY AUTOINCREMENT,
        node_id INTEGER,
        organization_id INTEGER,
        CONSTRAINT organization_owning_node
            FOREIGN KEY (organization_node_id)
            REFERENCES organization(organization_id),
        CONSTRAINT node_owning_organization
            FOREIGN KEY (node_id)
            REFERENCES federated_node(node_id)

    );`)
    // 4
    const user = db.prepare(
        `CREATE TABLE IF NOT EXISTS user_table (
        user_id INTEGER PRIMARY KEY AUTOINCREMENT,
        email TEXT NOT NULL,
        name TEXT NOT NULL,
        password TEXT NOT NULL,
        organization_id INTEGER,
        CONSTRAINT user_organization
            FOREIGN KEY (organization_id)
            REFERENCES organization(organization_id)
    );`)
    // 5
    const node_subscribers = db.prepare(`
        CREATE TABLE IF NOT EXISTS node_subscribers (
        subscription_id INTEGER PRIMARY KEY AUTOINCREMENT,
        subscribed_node_id INTEGER,
        subscribing_node_id INTEGER,
        CONSTRAINT node_subscribed
            FOREIGN KEY (subscribed_node_id)
            REFERENCES federated_node(node_id),
        CONSTRAINT node_subscribing
            FOREIGN KEY (subscribing_node_id)
            REFERENCES federated_node(node_id)

    );`)
    // 6
    const location = db.prepare(
        `CREATE TABLE IF NOT EXISTS location (
        location_id INTEGER PRIMARY KEY AUTOINCREMENT,
        location_name TEXT NOT NULL,
        address TEXT NOT NULL,
        latitude TEXT NOT NULL,
        longitude TEXT NOT NULL,
        security TEXT NOT NULL,
        organization_id INTEGER,
        CONSTRAINT location_organization
            FOREIGN KEY (organization_id)
            REFERENCES organization(organization_id)

    );`)
    // 7
    const inventory = db.prepare(
        `CREATE TABLE IF NOT EXISTS inventory (
        inventory_id INTEGER PRIMARY KEY AUTOINCREMENT,
        inventory_name TEXT NOT NULL,
        item_type TEXT NOT NULL,
        quantity INTEGER,
        security TEXT NOT NULL,
        location_id INTEGER,
        CONSTRAINT inventory_location
            FOREIGN KEY (location_id)
            REFERENCES location(location_id)
    );`)
    //8
    const order = db.prepare(
        `CREATE TABLE IF NOT EXISTS order_table (
        order_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
        status TEXT NOT NULL,
        security TEXT NOT NULL,
        source_location_id INTEGER NOT NULL,
        destination_location_id INTEGER NOT NULL,
        organization_id INTEGER,
        created DATETIME NOT NULL DEFAULT (strftime('%Y-%m-%d %H:%M:%S', 'now', 'localtime')),
        CONSTRAINT order_organization
            FOREIGN KEY (organization_id)
            REFERENCES organization(organization_id)
    );`)
    //9
    const order_item = db.prepare(
        `CREATE TABLE IF NOT EXISTS order_item (
        order_item_id INTEGER PRIMARY KEY AUTOINCREMENT,
        item_type TEXT NOT NULL,
        requested_quantity INTEGER NOT NULL,
        actual_quantity INTEGER NOT NULL,
        security TEXT NOT NULL,
        order_id INTEGER,
        CONSTRAINT order_order_item
            FOREIGN KEY (order_id)
            REFERENCES order_table(order_id)
    );`)

    federated_node.run()
    organization.run()
    organization_node.run()
    user.run()
    node_subscribers.run()
    location.run()
    inventory.run()
    order.run()
    order_item.run()

    return {
        db
    }
};

export type Context = inferAsyncReturnType<typeof createContext>;