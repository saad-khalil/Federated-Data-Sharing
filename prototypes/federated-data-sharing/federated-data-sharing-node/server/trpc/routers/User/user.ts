import { z } from "zod";
import { publicProcedure, router } from "../../trpc";

import type { UserResponse, User, UserAuthenticationResponse, DBUser } from "../../../types/User/User";
import type { DBOrganization, Organization } from "../../../types/Organization/Organization"
import type { DBLocation, Location } from "~/server/types/Location/Location";
import type { DBOrder, DBOrderItem, Order } from "~/server/types/Order/Order";
import type { DBInventory } from "~/server/types/Inventory/Inventory";


export const userRouter = router({
    getUsers: publicProcedure
    .query( ({ ctx }) => {
        const fetchUsers = ctx.db.prepare(`
        SELECT user_id, email, name, organization_id FROM user_table
        `)
        const users = fetchUsers.all() as User[];
        return {
            users
        }
    }),
    getUser: publicProcedure
    .input(
        z.object({
            email: z.string().email()
        })
    )
    .query( ({ input, ctx }) => {
        const user = ctx.db.prepare(`SELECT * FROM user_table WHERE email = ?`).get(input.email) as User

        const response : UserResponse = {
            message: "Successfully Added an User",
            success: true,
            payload: user

        }

        return {
            response
        }
    }),
    register: publicProcedure
    .input(
        z.object({
            email: z.string().email(),
            password: z.string(),
            name: z.string(),
            secret_key: z.string(),
        })
    )
    .query( ({ input, ctx }) => {
        let response : UserAuthenticationResponse = {
            message: "error occured before db transaction",
            success: false,
            payload: null
        }

        const fetchOrganization = ctx.db.prepare(`
            SELECT * FROM organization o WHERE o.secret_key = '${input.secret_key}'
        `).get() as Organization

        const locationsForOrganization : DBLocation[] = ctx.db.prepare(`
            SELECT * FROM location l
            INNER JOIN organization o ON l.organization_id = o.organization_id
            WHERE o.organization_id = ${fetchOrganization.organization_id}
        `).all() as DBLocation[]

        const ordersForOrganization : DBOrder[] = ctx.db.prepare(`
            SELECT * FROM order_table ot
            INNER JOIN organization o ON ot.organization_id = o.organization_id
            WHERE o.organization_id = ${fetchOrganization.organization_id}
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

        const name = input.name.split(" ")

        let newUser: DBUser;

        if( name.length == 1){
            newUser = {
                email: input.email,
                password: input.password,
                name: input.name,
                organization_id: fetchOrganization.organization_id
            }
        }
        else {
            newUser = {
                email: input.email,
                password: input.password,
                name: ` ${name[0]} ${name[name.length - 1]}`,
                organization_id: fetchOrganization.organization_id
            }
        }

        const _insert_user = ctx.db.prepare(`
        INSERT INTO user_table (email, name, password, organization_id) VALUES ( ?, ?, ?, ? )
        `).bind(newUser.email, newUser.name, newUser.password, newUser.organization_id).run()


        const subscribed_organiations = ctx.db.prepare(`
            SELECT * FROM organization o
            INNER JOIN organization_node n ON o.organization_id = n.organization_id
            INNER JOIN node_subscribers ns ON n.node_id = ns.subscribed_node_id
            WHERE o.organization_id = ? AND ( o.security = 'public' OR o.security = 'subscribe' )
        `).bind(fetchOrganization.organization_id).all() as Organization[]

        const user = ctx.db.prepare(`SELECT * FROM user_table WHERE email = ?`).get(input.email) as User

        const organization : Organization = {
            organization_id: fetchOrganization.organization_id,
            name: fetchOrganization.name,
            organization_type: fetchOrganization.organization_type,
            secret_key: fetchOrganization.secret_key,
            security: fetchOrganization.security,
            orders: orders,
            locations: locations
        }

        response = {
            message: "Successfully Added an User",
            success: true,
            payload: {
                user: user,
                organization: organization,
                organization_subscribers: subscribed_organiations
            }

        }

        return {
            response
        }
    }),
    authenticate: publicProcedure
    .input(
        z.object({
            email: z.string().email(),
            password: z.string()
        })
    )
    .query( ({ input, ctx }) => {
        let response : UserAuthenticationResponse = {
            message: "error occured before db transaction",
            success: false,
            payload: null
        }
        const user = ctx.db.prepare(`SELECT user_id, email, name,organization_id FROM user_table WHERE email = ?`).get(input.email) as User

        const fetchOrganization = ctx.db.prepare(`
            SELECT * FROM organization o WHERE o.organization_id= '${user.user_id}'
        `).get() as DBOrganization

        const locationsForOrganization : DBLocation[] = ctx.db.prepare(`
            SELECT * FROM location l
            INNER JOIN organization o ON l.organization_id = o.organization_id
            WHERE o.organization_id = ${fetchOrganization.organization_id}
        `).all() as DBLocation[]

        const ordersForOrganization : DBOrder[] = ctx.db.prepare(`
            SELECT * FROM order_table ot
            INNER JOIN organization o ON ot.organization_id = o.organization_id
            WHERE o.organization_id = ${fetchOrganization.organization_id}
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

        const subscribed_organiations = ctx.db.prepare(`
            SELECT * FROM organization o
            INNER JOIN organization_node n ON o.organization_id = n.organization_id
            INNER JOIN node_subscribers ns ON n.node_id = ns.subscribed_node_id
            WHERE o.organization_id = ?
        `).bind(fetchOrganization.organization_id).all() as Organization[]

        const organization : Organization = {
            organization_id: fetchOrganization.organization_id,
            name: fetchOrganization.name,
            organization_type: fetchOrganization.organization_type,
            secret_key: fetchOrganization.secret_key,
            security: fetchOrganization.security,
            orders: orders,
            locations: locations
        }

        response = {
            message: "Successfully Added an User",
            success: true,
            payload: {
                user: user,
                organization: organization,
                organization_subscribers: subscribed_organiations
            }

        }
        return {
            response
        }
    })
})