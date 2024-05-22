import { router } from '../trpc';
import { federatedNodeRouter } from './FederatedNode/federatedNode';
import { inventoryRouter } from './Inventory/inventory';
import { locationRouter } from './Location/location';
import { orderRouter } from './Order/order';
import { organizationRouter } from './Organization/organization';
import { userRouter } from './User/user';

export const appRouter = router({
    organizations : organizationRouter,
    users: userRouter,
    orders: orderRouter,
    locations: locationRouter,
    inventories: inventoryRouter,
    federatedNodes: federatedNodeRouter
});

// export type definition of API
export type AppRouter = typeof appRouter;