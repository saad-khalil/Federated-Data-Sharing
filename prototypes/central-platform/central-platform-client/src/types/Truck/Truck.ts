import { Item } from "../Item/Item"

export type Truck = {
    id: string,
    name: string,
    storage: Item[],
    maxStorage: number
}