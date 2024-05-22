import type { Location } from "../Location/Location"
import type { Order } from "../Order/Order"
import type { Privacy } from "../Privacy/Privacy"

export type Organization = {
    organization_id: number,
    name: string,
    organization_type: string,
    locations: Location[],
    secret_key: string,
    orders: Order[],
} & Privacy