export type OrderReponse = {
    message: string,
    success: boolean,
    payload:  Order | null
}

export type DBOrder = {
    order_id: number | bigint,
    status: string,
    security:  "private" | "public" | "subscribe",
    source_location_id: number,
    created: string,
    destination_location_id: number
    organization_id: number
}
export type DBOrderItem = {
    order_item_id: number,
    item_type: string,
    requested_quantity: number,
    actual_quantity: number,
    security:  "private" | "public" | "subscribe",
    order_id: number
}

export type Order = DBOrder & {
    ordered_items: DBOrderItem[]
}