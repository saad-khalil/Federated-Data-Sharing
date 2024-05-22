import { z } from "zod";
import { publicProcedure, router } from "../../trpc";

import type {Organization, OrganizationResponse, BatchOrganizationResponse, ShallowOrganization, DBOrganization} from "../../../types/Organization/Organization"
import type { DBLocation, Location } from "~/server/types/Location/Location";
import type { DBOrder, DBOrderItem } from "~/server/types/Order/Order";
import type { DBInventory, Inventory } from "~/server/types/Inventory/Inventory";
import type { Order } from "~/types/Order/Order";
import { DBFederatedNode } from "~/server/types/FederatedNode/federatedNode";

export const organizationRouter = router({
    getOrganizations: publicProcedure
    .query( ({ ctx }) => {
        const fetchInstitutions = ctx.db.prepare(`
        SELECT * FROM organization
        `)
        const organizations = fetchInstitutions.all()
        return {
            organizations
        }
    }),
    getSubscribedOrganizations: publicProcedure
    .input(
        z.object({
            user_id: z.number(),
            organization_id: z.number()

        })
    )
    .query( ({ input, ctx }) => {

        const query = ctx.db.prepare(`
            SELECT * FROM organization o
            INNER JOIN organization_node n ON o.organization_id = n.organization_id
            INNER JOIN node_subscribers ns ON n.node_id = ns.subscribed_node_id
            WHERE o.organization_id = ?

        `).bind(input.organization_id).all() as Organization[]

        const response : BatchOrganizationResponse = {
            message: "Fetched all nodes subscbrided to input node",
            success: false,
            payload: query
        }
        return {
            response
        }
    }),
    getOrganizationById: publicProcedure
    .input(
        z.object({
            organization_id : z.number(),
        })
    )
    .query( ({ input, ctx }) => {

        const response : OrganizationResponse = {
            success: true,
            message: "Successfully fetched Organization",
            payload: null
        }


        const fetchedOrganization: DBOrganization = ctx.db.prepare(`
            SELECT * FROM organization WHERE organization_id = ${input.organization_id}
        `).get() as DBOrganization

        const locationsForOrganization : DBLocation[] = ctx.db.prepare(`
            SELECT * FROM location l
            INNER JOIN organization o ON l.organization_id = o.organization_id
            WHERE o.organization_id = ${input.organization_id}
        `).all() as DBLocation[]

        const ordersForOrganization : DBOrder[] = ctx.db.prepare(`
            SELECT * FROM order_table ot
            INNER JOIN organization o ON ot.organization_id = o.organization_id
            WHERE o.organization_id = ${input.organization_id}
        `).all() as DBOrder[]

        const locations : Location[] = locationsForOrganization.map((dblocation: DBLocation) => {
            const inventories = ctx.db.prepare(`
                SELECT * FROM inventory i
                WHERE i.location_id = '${dblocation.location_id}'
            `).all() as DBInventory[]
            return  {...dblocation, inventories }
        });

        const orders : Order[] = ordersForOrganization.map((dborder: DBOrder) => {
            const ordered_items = ctx.db.prepare(`
                SELECT * FROM order_item oi
                WHERE oi.order_id = '${dborder.order_id}'
            `).all() as DBOrderItem[]
            return  {...dborder, ordered_items}
        });

        const organization : Organization = {
            organization_id: fetchedOrganization.organization_id,
            name: fetchedOrganization.name,
            organization_type: fetchedOrganization.organization_type,
            secret_key: fetchedOrganization.secret_key,
            security: fetchedOrganization.security,
            orders: orders,
            locations: locations
        }

        response.payload = organization


        return {
            response
        }
    }),
    addOrganization : publicProcedure
    .input(
        z.object({
            name: z.string(),
            secret_key: z.string().min(20),
            organization_type: z.string(),
            security: z.literal("private").or(z.literal("subscribe")).or(z.literal("public"))
        })
    )
    .query( ({ input, ctx }) => {
        let response : OrganizationResponse = {
            message: "error occured before db transaction",
            success: false,
            payload: null
        }

        const newOrganization : ShallowOrganization = {
            name: input.name,
            organization_type: input.organization_type,
            security: input.security,
            secret_key: input.secret_key
        }

        const insert_organization = ctx.db.prepare(`
            INSERT INTO organization (name, secret_key, organization_type, security) VALUES (?, ?, ?, ?)
        `).run(newOrganization.name, newOrganization.secret_key, newOrganization.organization_type, newOrganization.security)

        const getInsertedOrganization = ctx.db.prepare(`
            SELECT * FROM organization o WHERE o.name = '${newOrganization.name}'
        `).get() as Organization

        const insert_node = ctx.db.prepare(`
            INSERT INTO federated_node ( name, security ) VALUES ( ?, ? )
        `).run( newOrganization.name, newOrganization.security)

        const _link_node_to_organiation = ctx.db.prepare(`
            INSERT INTO organization_node ( node_id, organization_id ) VALUES ( ?, ? )
        `).run( insert_node.lastInsertRowid, insert_organization.lastInsertRowid)

        response = {
            message: "Successfully Added Node to Network",
            success: true,
            payload: getInsertedOrganization
        }

        return {
            response
        }
    }),

    subscribe: publicProcedure
    .input(
        z.object({
            organization_id: z.number(),
            secret_key: z.string()
        })
    )
    .query( ({input, ctx}) => {

        const response : OrganizationResponse = {
            message: "Successfully sibscribed to node",
            success: true,
            payload: null
        }

        const get_organization_by_secret_key = ctx.db.prepare(`
            SELECT * from organization o WHERE o.secret_key = '${input.secret_key}'
        `).get() as DBOrganization

        const get_node_to_subscribe = ctx.db.prepare(`
            SELECT * FROM federated_node fn
            INNER JOIN organization_node o_n ON fn.node_id = o_n.node_id
            WHERE o_n.organization_id = ?
        `).bind(get_organization_by_secret_key.organization_id).get() as DBFederatedNode

        const get_node_subscriber = ctx.db.prepare(`
            SELECT * FROM federated_node fn
            INNER JOIN organization_node on ON fn.node_id = on.node_id
            WHERE on.organization_id =
        `).bind(input.organization_id).get() as DBFederatedNode

        const _subscribe = ctx.db.prepare(`
            INSERT INTO node_subscribers ( subscribed_node_id , subscribing_node_id ) VALUES (?, ?)
        `).bind(get_node_to_subscribe.node_id, get_node_subscriber.node_id).run()

        response.payload = get_organization_by_secret_key as Organization

        return {
            get_organization_by_secret_key
        }

    })

})