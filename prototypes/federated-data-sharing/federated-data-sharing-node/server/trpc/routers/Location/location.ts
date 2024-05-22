import { z } from "zod";
import { publicProcedure, router } from "../../trpc";

import type {DBLocation, Location, LocationResponse } from "../../../types/Location/Location"
import { Inventory } from "~/server/types/Inventory/Inventory";


export const locationRouter = router({
    getLocations: publicProcedure
    .query( ({ ctx }) => {
        const fetchLocations = ctx.db.prepare(`
        SELECT * FROM location
        `)
        const locations = fetchLocations.all();
        return {
            locations
        }
    }),
    getLocationsByOrganizationId: publicProcedure
    .input(
        z.object({
            organiation_id: z.number(),
            user_id: z.number()
        })
    )
    .query( ({ input, ctx }) => {

        const locations = ctx.db.prepare(`
            SELECT * FROM inventory i
            INNER JOIN organization o ON i.inventory_id = o.organization_id
            INNER JOIN user_table ut ON o.organization_id = u.organization_id
            WHERE o.organization_id = ? AND ut.user_id = ?
        `).bind(input.organiation_id, input.user_id).all() as Location[];


        const payload_body = locations.map((dblocation: Location) => {
            const inventories = ctx.db.prepare(`
                SELECT * FROM inventory i
                WHERE i.location_id = '${dblocation.location_id}'
            `).all() as Inventory[]
            return  Object.assign(dblocation, inventories)
        });

        const response : LocationResponse = {
            message: "Fecthed all locations for an organization",
            success: true,
            payload: payload_body
        }
        return {
            response
        }
    }),
    addLocation: publicProcedure
    .input(
        z.object({
            name: z.string(),
            address: z.string(),
            latitude: z.string(),
            longitude: z.string(),
            security: z.literal("private").or(z.literal("subscribe")).or(z.literal("public")),
            organization_id: z.number()
        })
    )
    .query( ({ input, ctx }) => {
        let response : LocationResponse = {
            message: "error occured before db transaction",
            success: false,
            payload: null
        }

        const newLocation : DBLocation = {
            location_id: 1,
            name: input.name,
            address: input.address,
            latitude: input.latitude,
            longitude: input.longitude,
            security: input.security,
            organization_id: input.organization_id
        }

        const _insertLocation = ctx.db.prepare(`
        INSERT INTO location (location_name, address, latitude, longitude, security, organization_id) VALUES (?, ?, ?, ?, ?, ?)
        `).run(input.name, newLocation.address, newLocation.latitude, newLocation.longitude, newLocation.security, newLocation.organization_id)

        const getInsertedLocation = ctx.db.prepare(`
        SELECT * FROM location WHERE location_name = '${newLocation.name}'
        `)

        const location = getInsertedLocation.get();

        response = {
            message: "Successfully Added a Location",
            success: true,
            payload: location as Location
        }

        return {
            response
        }
    })
})