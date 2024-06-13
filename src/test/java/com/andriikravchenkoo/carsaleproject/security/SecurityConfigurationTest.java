package com.andriikravchenkoo.carsaleproject.security;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import com.andriikravchenkoo.carsaleproject.controller.AuthenticationController;
import com.andriikravchenkoo.carsaleproject.model.entity.User;
import com.andriikravchenkoo.carsaleproject.model.enums.Role;
import com.andriikravchenkoo.carsaleproject.security.configuration.SecurityConfiguration;
import com.andriikravchenkoo.carsaleproject.security.facade.RegistrationServiceFacade;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = AuthenticationController.class)
@Import(SecurityConfiguration.class)
class SecurityConfigurationTest {

    @Autowired private MockMvc mockMvc;

    @MockBean private RegistrationServiceFacade registrationServiceFacade;

    @MockBean private UserDetailsService userDetailsService;

    @Test
    void testShouldGiveRegisterPageWithoutAuthentication() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/authentication/register"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("authentication/register"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("registerRequestDto"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("roles"));
    }

    @Test
    void testShouldGiveLoginPageWithoutAuthentication() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/authentication/login"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("authentication/login"));
    }

    @Test
    void testShouldNotAllowAccessToProtectedResourcesWithoutAuthentication() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/announcement/create"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(
                        MockMvcResultMatchers.redirectedUrl(
                                "http://localhost/authentication/login"));
    }

    @Test
    void testShouldAllowAccessToSellerProtectedResourcesWithProperAuthentication()
            throws Exception {
        User customUserDetails =
                new User(
                        "Firstname",
                        "Lastname",
                        "seller@gmail.com",
                        "password",
                        "test",
                        Role.SELLER);

        when(userDetailsService.loadUserByUsername("seller@gmail.com"))
                .thenReturn(customUserDetails);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/announcement/create")
                                .with(user(customUserDetails)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testShouldNotAllowAccessToSellerProtectedResourcesWithProperAuthentication()
            throws Exception {
        User customUserDetails =
                new User(
                        "Firstname",
                        "Lastname",
                        "customer@gmail.com",
                        "password",
                        "test",
                        Role.CUSTOMER);

        when(userDetailsService.loadUserByUsername("customer@gmail.com"))
                .thenReturn(customUserDetails);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/announcement/create")
                                .with(user(customUserDetails)))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
}
