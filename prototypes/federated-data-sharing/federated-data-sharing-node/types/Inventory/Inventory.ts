import type { Privacy } from "../Privacy/Privacy"

export type Inventory = {
    inventory_id: number,
    inventory_name: string,
    item_type: string,
    quantity: number,
    location_id: number,
} & Privacy