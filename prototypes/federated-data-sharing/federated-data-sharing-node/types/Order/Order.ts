import type{ OrderItem } from "../OrderItem/OrderItem"
import type { Privacy } from "../Privacy/Privacy"

export type Order = {
    order_id: number | bigint,
    status: string,
    security: string,
    source_location_id: number,
    destination_location_id: number
    created: string,
    organization_id: number,
    ordered_items: OrderItem[]
} & Privacy