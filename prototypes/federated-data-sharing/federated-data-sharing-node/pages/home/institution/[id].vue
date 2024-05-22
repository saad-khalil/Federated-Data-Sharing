<script lang="ts" setup>
    import "leaflet/dist/leaflet.css";
    import { LMap, LTileLayer, LMarker, LPopup } from "@vue-leaflet/vue-leaflet";
    import { PointExpression } from "leaflet";
    import { useOrganizationStore } from "~/stores/Organization/organizationStore";

    import { useUserStore } from "~/stores/User/userStore";

    import type { Organization } from "~/types/Organization/Organization";

    const router = useRoute()
    const organizationStore = useOrganizationStore();

    const { getOrganizationById } = organizationStore

    const userStore = useUserStore()
    const { user } = userStore
    const current_org : Ref<Organization> = ref<Organization>({} as Organization)
    onMounted(() => {
        if(user === undefined || user.email == undefined){
            navigateTo("/")
        }

        console.log(current_org)
    })

    useHead({
        title: "Federated - Map"
    })

    onBeforeMount( async () => {

        let address = ""
        let port = 0
        if (process.client) {
            const location = window.location.href
            address = location.substring(7,16)
            port = Number(location.substring(17,21))
        }
        const data = await getOrganizationById(Number(router.params.id))
        if(data){
            current_org.value = (data) as Organization
        }
    })
    let zoom = ref(7);
    let center = ref<PointExpression>([52.1926, 5.2913]);

</script>

<template>
    <div>
        <div class="h-screen w-full relative">
            <l-map
                ref="map"
                v-model:zoom="zoom"
                v-model:center="center"
                :useGlobalLeaflet="false"
            >
                <l-tile-layer
                url="https://tiles.stadiamaps.com/tiles/alidade_smooth_dark/{z}/{x}/{y}{r}.png"
                layer-type="base"
                name="Locations map"
                >
                <l-marker
                    v-for="(location, locationIndex) in current_org.locations"
                    :key="locationIndex"
                    :lat-lng="[parseFloat(location.latitude), parseFloat(location.longitude)]"
                    >
                        <l-popup>
                            <div>
                            <strong>{{ location.name }}</strong>
                            </div>
                            <div>{{ location.address }}</div>
                        </l-popup>
                    </l-marker>
            </l-tile-layer>
            </l-map>
        </div>
    </div>
</template>