import { Inventory } from "../Inventory/Inventory"

export type Location = {
    id: string
    address: string,
    latitude: string,
    longitude: string,
    inventory: Inventory[]
    sourceOrders: []
    destinationOrders: []
}