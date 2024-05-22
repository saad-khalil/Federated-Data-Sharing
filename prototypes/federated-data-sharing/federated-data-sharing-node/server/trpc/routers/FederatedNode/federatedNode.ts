import { z } from 'zod';
import { publicProcedure, router } from '../../trpc';

import { DBFederatedNode, FederatedNodeResponse} from "../../../types/FederatedNode/federatedNode"

export const federatedNodeRouter = router({
    getNodes : publicProcedure
    .query( ({ ctx }) => {
        const fetchNodes = ctx.db.prepare(`
        SELECT * FROM federated_node
        `)
        const nodes = fetchNodes.all()
        return {
            nodes
        }
    }),
    getNode: publicProcedure
    .input(
        z.object({
            name: z.string()
        })

    )
    .query( ({ input, ctx }) => {
        let response : FederatedNodeResponse = {
            message: "error occured before db transaction",
            success: false,
            payload: null
        };

        const node = ctx.db.prepare(`SELECT * FROM federated_node WHERE name = ?`).get(input.name)

        response = {
            message: "Successfully Fetched Node",
            success: true,
            payload: node

        }

        return {
            response
        }
    }),

    subscribe : publicProcedure
    .input(
        z.object({
            subscribed_node_id: z.number(),
            subscribing_node_id: z.number()
        })
    )
    .query( ({ input, ctx }) => {

        const _subscribe = ctx.db.prepare(`
            INSERT INTO node_subscribers ( subscribed_node_id , subscribing_node_id ) VALUES (?, ?)
        `).bind(input.subscribed_node_id, input.subscribing_node_id).run()

        const response : FederatedNodeResponse = {
            message: "Successfully Subscribed to Node",
            success: false,
            payload: null
        };
        return {
            response
        }
    }),

    addNode : publicProcedure
    .input(
        z.object({
            name: z.string(),
            security: z.literal("private").or(z.literal("subscribe")).or(z.literal("public"))
        })
    )
    .query( ({ input, ctx }) => {
        let response : FederatedNodeResponse = {
            message: "error occured before db transaction",
            success: false,
            payload: null
        };

        const fetchOrganization = ctx.db.prepare(`
        SELECT * FROM federated_node WHERE name = ${input.name}
        `)
        const owner_organization = fetchOrganization.all()

        if(owner_organization.length > 0){
            const newNode : DBFederatedNode = {
                name: input.name,
                security: input.security
            }
            const insertNode = ctx.db.prepare(`
                INSERT INTO federated_node (name, owner, security) VALUES (?, ?, ?)
            `).bind(newNode.name, newNode.security)

            insertNode.run(newNode);


            const insertedNode = ctx.db.prepare(`SELECT * FROM federated_node WHERE name = ?`).get(input.name)

            response = {
                message: "Successfully Added New Node",
                success: true,
                payload: insertedNode
            }
        }
        else {
            response =  {
                message: "organization note found",
                success: false,
                payload: null
            }
        }

        return {
            response
        }
    })
})