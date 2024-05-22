import { z } from "zod";
import { publicProcedure, router } from "../../trpc";

import type {DBOrder, DBOrderItem, Order, OrderReponse } from "../../../types/Order/Order"

export const orderRouter = router({
    getOrders: publicProcedure
    .query( ({ ctx }) => {
        const fetchOrders = ctx.db.prepare(`
        SELECT * FROM order_table
        `)
        const orders = fetchOrders.all();
        return {
            orders
        }
    }),
    addOrder: publicProcedure
    .input(
        z.object({
            organization_id : z.number(),
            source_location_id: z.number(),
            destination_location_id: z.number(),
            status: z.string(),
            security: z.literal("private").or(z.literal("subscribe")).or(z.literal("public")),
        })
    )
    .query( ({ input, ctx }) => {
        const insert_order = ctx.db.prepare(`
            INSERT INTO order_table (status, security, source_location_id, destination_location_id, organization_id) VALUES ( ?, ?, ?, ?, ?)
        `).bind(input.status, input.security, input.source_location_id, input.destination_location_id, input.organization_id).run()


        const inserted_order = ctx.db.prepare(`
            SELECT * FROM order_table o WHERE o.order_id = ${insert_order.lastInsertRowid}
        `).get() as DBOrder

        const ordered_items = ctx.db.prepare(`
            SELECT * FROM order_item o WHERE o.order_id = ${insert_order.lastInsertRowid}
        `).all() as DBOrderItem[]

        const order: Order = {
            order_id: insert_order.lastInsertRowid,
            status: inserted_order.status,
            security: inserted_order.security,
            created: inserted_order.created,
            source_location_id: inserted_order.source_location_id,
            destination_location_id: inserted_order.destination_location_id,
            organization_id: inserted_order.organization_id,
            ordered_items: ordered_items
        }

        const response : OrderReponse = {
            message: "Successfully added Order",
            success: true,
            payload: order
        }
        return {
            response
        }
    }),

    addOrderWithItems : publicProcedure
    .input(
        z.object({
            organization_id : z.number(),
            source_location_id: z.number(),
            destination_location_id: z.number(),
            status: z.string(),
            security: z.literal("private").or(z.literal("subscribe")).or(z.literal("public")),
            items: z.array(
                z.object({
                    item_type: z.string(),
                    requested_quantity: z.number(),
                    actual_quantity: z.number(),
                    security: z.literal("private").or(z.literal("subscribe")).or(z.literal("public")),
                })
            )
        })
    )
    .query( ({ input, ctx}) => {

        const response : OrderReponse = {
            message: "error occured before db transaction",
            success: false,
            payload: null
        }

        const insert_order = ctx.db.prepare(`
            INSERT INTO order_table VALUES (status, security, source_location_id, destination_location_id, organization_id) VALUES (?, ?, ?, ?, ?)
        `).bind(input.status, input.security, input.source_location_id, input.destination_location_id, input.organization_id).run()

        const item_values = ""

        input.items.forEach((item) => {
            item_values.concat(`( '${item.item_type}', ${item.requested_quantity}, ${item.actual_quantity}, '${item.security}', ${insert_order.lastInsertRowid} )`)
        })

        const _insert_order_items = ctx.db.prepare(`
            INSERT INTO order_item (item_type, requested_quantity, actual_quantity, security, order_id) VALUES ${item_values}
        `).run()

        const inserted_order = ctx.db.prepare(`
            SELECT * FROM order_table o WHERE o.order_id = ${insert_order.lastInsertRowid}
        `).get() as DBOrder

        const ordered_items = ctx.db.prepare(`
            SELECT * FROM order_item o WHERE o.order_id = ${insert_order.lastInsertRowid}
        `).all() as DBOrderItem[]

        const order: Order = {
            order_id: insert_order.lastInsertRowid,
            status: inserted_order.status,
            security: inserted_order.security,
            created: inserted_order.created,
            source_location_id: inserted_order.source_location_id,
            destination_location_id: inserted_order.destination_location_id,
            organization_id: inserted_order.organization_id,
            ordered_items: ordered_items
        }

        response.payload = order

        return {
            response
        }
    })
})