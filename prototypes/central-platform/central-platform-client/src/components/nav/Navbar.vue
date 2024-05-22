<script lang="ts" setup>
import { useToast } from "vue-toastification";
import { useOrganizationStore } from "~/stores/Organization/OrganizationStore";

const route = useRoute();
function logOut() {
  const toast = useToast();

  const userToken = useCookie("userToken");
  userToken.value = null;
  
  const userRole = useCookie("userRole");
  userRole.value = null;

  const store = useOrganizationStore() 
  store.reset()

  setTimeout(() => {
    toast.info("Logged out");
  }, 600);
  navigateTo("/");
}
</script>

<template>
  <div
    class="w-full h-16 flex flex-row items-center justify-between bg-emerald-500 p-4"
  >
    <div class="flex">
      <NuxtLink
        to="/home"
        class="border-2 mr-1 border-transparent hover:bg-emerald-400 hover:border-emerald-300 transition duration-500 p-3 rounded-md"
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          viewBox="0 0 24 24"
          fill="white"
          class="w-6 h-6"
        >
          <path
            d="M11.47 3.84a.75.75 0 011.06 0l8.69 8.69a.75.75 0 101.06-1.06l-8.689-8.69a2.25 2.25 0 00-3.182 0l-8.69 8.69a.75.75 0 001.061 1.06l8.69-8.69z"
          />
          <path
            d="M12 5.432l8.159 8.159c.03.03.06.058.091.086v6.198c0 1.035-.84 1.875-1.875 1.875H15a.75.75 0 01-.75-.75v-4.5a.75.75 0 00-.75-.75h-3a.75.75 0 00-.75.75V21a.75.75 0 01-.75.75H5.625a1.875 1.875 0 01-1.875-1.875v-6.198a2.29 2.29 0 00.091-.086L12 5.43z"
          />
        </svg>
      </NuxtLink>
      <NuxtLink
        to="/database"
        class="border-2 mr-1 border-transparent hover:bg-emerald-400 hover:border-emerald-300 transition duration-500 p-3 rounded-md"
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          viewBox="0 0 24 24"
          fill="white"
          class="w-6 h-6"
        >
          <path
            d="M21 6.375c0 2.692-4.03 4.875-9 4.875S3 9.067 3 6.375 7.03 1.5 12 1.5s9 2.183 9 4.875z"
          />
          <path
            d="M12 12.75c2.685 0 5.19-.586 7.078-1.609a8.283 8.283 0 001.897-1.384c.016.121.025.244.025.368C21 12.817 16.97 15 12 15s-9-2.183-9-4.875c0-.124.009-.247.025-.368a8.285 8.285 0 001.897 1.384C6.809 12.164 9.315 12.75 12 12.75z"
          />
          <path
            d="M12 16.5c2.685 0 5.19-.586 7.078-1.609a8.282 8.282 0 001.897-1.384c.016.121.025.244.025.368 0 2.692-4.03 4.875-9 4.875s-9-2.183-9-4.875c0-.124.009-.247.025-.368a8.284 8.284 0 001.897 1.384C6.809 15.914 9.315 16.5 12 16.5z"
          />
          <path
            d="M12 20.25c2.685 0 5.19-.586 7.078-1.609a8.282 8.282 0 001.897-1.384c.016.121.025.244.025.368 0 2.692-4.03 4.875-9 4.875s-9-2.183-9-4.875c0-.124.009-.247.025-.368a8.284 8.284 0 001.897 1.384C6.809 19.664 9.315 20.25 12 20.25z"
          />
        </svg>
      </NuxtLink>
      <NuxtLink
        to="/stats"
        class="border-2 mr-1 border-transparent hover:bg-emerald-400 hover:border-emerald-300 transition duration-500 p-3 rounded-md"
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          viewBox="0 0 24 24"
          fill="white"
          class="w-6 h-6"
        >
          <path
            fill-rule="evenodd"
            d="M3 6a3 3 0 013-3h12a3 3 0 013 3v12a3 3 0 01-3 3H6a3 3 0 01-3-3V6zm4.5 7.5a.75.75 0 01.75.75v2.25a.75.75 0 01-1.5 0v-2.25a.75.75 0 01.75-.75zm3.75-1.5a.75.75 0 00-1.5 0v4.5a.75.75 0 001.5 0V12zm2.25-3a.75.75 0 01.75.75v6.75a.75.75 0 01-1.5 0V9.75A.75.75 0 0113.5 9zm3.75-1.5a.75.75 0 00-1.5 0v9a.75.75 0 001.5 0v-9z"
            clip-rule="evenodd"
          />
        </svg>
      </NuxtLink>
      <NuxtLink
        to="/user"
        class="border-2 mr-1 border-transparent hover:bg-emerald-400 hover:border-emerald-300 transition duration-500 p-3 rounded-md"
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          viewBox="0 0 24 24"
          fill="white"
          class="w-6 h-6"
        >
          <path
            d="M4.5 6.375a4.125 4.125 0 118.25 0 4.125 4.125 0 01-8.25 0zM14.25 8.625a3.375 3.375 0 116.75 0 3.375 3.375 0 01-6.75 0zM1.5 19.125a7.125 7.125 0 0114.25 0v.003l-.001.119a.75.75 0 01-.363.63 13.067 13.067 0 01-6.761 1.873c-2.472 0-4.786-.684-6.76-1.873a.75.75 0 01-.364-.63l-.001-.122zM17.25 19.128l-.001.144a2.25 2.25 0 01-.233.96 10.088 10.088 0 005.06-1.01.75.75 0 00.42-.643 4.875 4.875 0 00-6.957-4.611 8.586 8.586 0 011.71 5.157v.003z"
          />
        </svg>
      </NuxtLink>
    </div>
    <h1 class="text-white font-semibold text-[16px] text-center absolute left-1/2 transform -translate-x-1/2">
      DASHBOARD - {{ route.name?.toString().toUpperCase() }}
    </h1>
    <button
      v-if="useCookie('userToken').value"
      @click="logOut"
      class="text-white font-semibold text-[16px] border border-white px-4 py-2 rounded-lg"
    >
      Logout
    </button>
  </div>
</template>
