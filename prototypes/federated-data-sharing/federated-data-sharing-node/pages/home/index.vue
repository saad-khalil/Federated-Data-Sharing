<script setup lang="ts">

    import { useUserStore } from '~/stores/User/userStore';
    import { useOrganizationStore } from '~/stores/Organization/organizationStore';

    const userStore = useUserStore()
    const organizationStore = useOrganizationStore()

    const { getUser, user } = userStore;
    const { getOrganizations, get_selected_organization } = organizationStore

    const isSidemenuOpen : Ref<boolean> = ref<boolean>(false);

    onMounted(() => {
        if(user === undefined || user.email == undefined){
            navigateTo("/")
        }
    })

    useHead({
        title: "Federated - Home"
    })

</script>

<template>
    <div class="h-screen">
        <main class="overflow-auto">
            <div>
            <div class="w-full">
                <div class="p-8 mb-5 h-screen">
                <h1 class="text-3xl font-bold mb-10 text-white">Welcome back: {{ getUser.name }}</h1>

                <hr class="my-10 border-neutral-800">

                <div class="grid grid-cols-2 gap-x-20">
                    <div>
                    <h2 class="text-3xl font-bold mb-4 text-white">Overview</h2>

                    <div class="grid grid-cols-2 gap-4">
                        <div class="col-span-2">
                            <div class="p-4 bg-emerald-600 rounded-md min-h-[200px] flex flex-col items-start justify-between">
                                <div class="font-bold text-xl text-gray-800 leading-none">Current Inventory Graph</div>
                                <div class="mt-5 flex flex-row items-center justify-end w-full">
                                    <button type="button" class="inline-flex justify-end py-2 px-3 rounded-md bg-neutral-900 text-white text-sm font-semibold transition">
                                        Go to Inventory
                                    </button>
                                </div>
                            </div>
                        </div>
                        <div class="p-4 bg-neutral-800 rounded-md min-h-[150px] text-white">
                            <div class="font-bold text-2xl leading-none">20</div>
                            <div class="mt-2">Trucks on the Go</div>
                        </div>
                            <div class="p-4 bg-neutral-800 rounded-md min-h-[150px] text-white">
                            <div class="font-bold text-2xl leading-none">5,5</div>
                        <div class="mt-2">Tracked hours</div>
                        </div>
                        <div class="col-span-2 min-h-[200px]">
                            <div class="p-4 bg-neutral-800 h-full rounded-md text-white">
                                <div class="font-bold text-xl leading-none">Organiation Map</div>
                                <div class="mt-2">5 of 8 contain updated info</div>
                            </div>
                        </div>
                    </div>
                    </div>
                    <div>
                    <h2 class="text-3xl font-bold mb-4 text-white">Today's Orders</h2>

                    <div class="space-y-4 overflow-y-auto h-[700px] no-scrollbar">
                        <div
                        v-for="(order, index) in get_selected_organization.orders"
                        :key="index"
                        class="p-4 border-2 border-emerald-500/20 hover:border-emerald-500 rounded-md text-white space-y-2 transition duration-300">
                            <div class="flex justify-between">
                                <div class="text-white/90 text-xs">Order ID: {{ order.order_id }}</div>
                                <div class="text-white/90 text-xs"> 20:30</div>
                            </div>
                            <div class="text-sm text-white/60">
                                <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" fill="currentColor" class="text-white inline align-middle mr-1" viewBox="0 0 16 16">
                                <path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zM8 4a.905.905 0 0 0-.9.995l.35 3.507a.552.552 0 0 0 1.1 0l.35-3.507A.905.905 0 0 0 8 4zm.002 6a1 1 0 1 0 0 2 1 1 0 0 0 0-2z"/>
                                </svg>
                                <h1 class="font-semibold text-[14px]">
                                    Source: id-{{ order.source_location_id }}
                                </h1>
                            </div>
                            <div class="text-sm text-white/60">
                                <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" fill="currentColor" class="text-white inline align-middle mr-1" viewBox="0 0 16 16">
                                <path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zM8 4a.905.905 0 0 0-.9.995l.35 3.507a.552.552 0 0 0 1.1 0l.35-3.507A.905.905 0 0 0 8 4zm.002 6a1 1 0 1 0 0 2 1 1 0 0 0 0-2z"/>
                                </svg>
                                <h1 class="font-semibold text-[14px]">
                                    Destination: id-{{ order.destination_location_id }}
                                </h1>
                            </div>
                        </div>

                    </div>
                    </div>
                </div>
                </div>
            </div>
            </div>
        </main>

        <LazySlideover
        :is-slide-open="isSidemenuOpen"
        @update:slide-open="isSidemenuOpen = $event"
        >
            <h1>Hello</h1>
        </LazySlideover>
        <NuxtPage />
    </div>

</template>

<style lang="scss" scoped>
.no-scrollbar::-webkit-scrollbar {
    display: none;
}

.no-scrollbar {
    -ms-overflow-style: none;
    scrollbar-width: none;
}
</style>