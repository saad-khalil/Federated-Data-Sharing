import { z } from "zod";
import { publicProcedure, router } from "../../trpc";

import type { DBInventory, Inventory, InventoryResponse } from "../../../types/Inventory/Inventory"

export const inventoryRouter = router({
    getInventories: publicProcedure
    .query(({ ctx }) => {
        const inventories = ctx.db.prepare('SELECT * FROM inventory').all() as Inventory[]
        return {
            inventories
        }
    }),
    getInventory: publicProcedure
    .query( ({ ctx }) => {
        const fetchInventory = ctx.db.prepare(`
        SELECT * FROM inventory
        `)
        const inventories = fetchInventory.all()
        return {
            inventories
        }
    }),
    getInventoriesForLocation: publicProcedure
    .input(
        z.object({
            user_id: z.number(),
            location_id: z.number()
        })
    )
    .query( ({ input, ctx }) => {

            const inventories = ctx.db.prepare(`
            SELECT * FROM inventory i
            INNER JOIN location l ON l.location_id = i.location_id
            INNER JOIN organization o ON o.organization_id = l.organization_id
            INNER JOIN user_table u ON u.organization_id = o.organization_id
            WHERE l.location_id = ? AND u.user_id = ?
            ORDER BY i.inventory_id
            `).bind(input.location_id, input.user_id).all() as DBInventory[]


            const response : InventoryResponse = {
                message: "Fetched all inventories given a location",
                success: true,
                payload: inventories as Inventory[]
            }

        return {
            response
        }
    }),
    addInventory: publicProcedure
    .input(
        z.object({
            item_type : z.string(),
            inventory_name : z.string(),
            quantity: z.number(),
            location_id: z.number(),
            security: z.literal("private").or(z.literal("subscribe")).or(z.literal("public"))
        })
    )
    .query( ({ input, ctx }) => {
        let response : InventoryResponse = {
            message: "error occured before db transaction",
            success: false,
            payload: null
        }

        const newInvenotry : DBInventory = {
            item_type: input.item_type,
            inventory_name: input.inventory_name,
            quantity: input.quantity,
            location_id: input.location_id,
            security: input.security
        }

        const _insertInventory = ctx.db.prepare(`
            INSERT INTO inventory (inventory_name, item_type, quantity, security, location_id) VALUES (?, ?, ?, ?, ?);
        `).bind(newInvenotry.inventory_name, newInvenotry.item_type, newInvenotry.quantity, newInvenotry.security, newInvenotry.location_id).run()

        const getInsertedInventory = ctx.db.prepare(`
        SELECT * FROM inventory WHERE inventory_name = '${newInvenotry.inventory_name}'
        `).get() as DBInventory


        response = {
            message: "Successfully Added an Invenotry",
            success: true,
            payload: getInsertedInventory
        }

        return {
            response
        }
    })
})