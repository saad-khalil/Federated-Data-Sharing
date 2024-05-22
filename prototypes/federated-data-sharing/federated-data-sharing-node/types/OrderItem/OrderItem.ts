import type { Privacy } from "../Privacy/Privacy"

export type OrderItem = {
    order_item_id: number,
    item_type: string,
    requested_quantity: number,
    actual_quantity: number,
    security:  "private" | "public" | "subscribe"
    order_id: number
} & Privacy