import { Inventory } from "../Inventory/Inventory"

export type LocationResponse = {
    message: string,
    success: boolean,
    payload: Location[] | Location | null
}

export type DBLocation = {
    location_id: number,
    name: string,
    address: string,
    latitude: string,
    longitude: string,
    security : "private" | "public" | "subscribe",
    organization_id: number
}

export type Location = DBLocation & {
    inventories: Inventory[]
}