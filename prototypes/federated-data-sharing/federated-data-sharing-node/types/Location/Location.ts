import type { Inventory } from "../Inventory/Inventory"
import type { Privacy } from "../Privacy/Privacy"

export type Location = {
    location_id: number,
    name: string,
    address: string,
    latitude: string,
    longitude: string,
    organization_id: number,
    inventories: Inventory[]
} & Privacy