package com.pettime.model;

import lombok.Getter;

/**
 * Enum representing the different roles a user can have in the system.
 * (FR) Enum représentant les différents rôles qu'un utilisateur peut avoir dans le système.
 * This enum defines the roles available for users, such as CLIENT, PETSHOP, and ADMIN.
 * Future roles can be easily added without modifying the core logic of the application.
 */
@Getter
public enum UserRole {

    /**
     * A client role, who is using the service to book pet-related services.
     * (FR) Rôle de client, qui utilise le service pour réserver des services liés aux animaux de compagnie.
     */
    CLIENT("Client"),

    /**
     * A pet shop role, representing businesses that offer pet-related services.
     * (FR) Rôle de boutique pour animaux, représentant les entreprises offrant des services pour animaux de compagnie.
     */
    PETSHOP("Pet Shop"),

    /**
     * An admin role, who has full control over the system.
     * (FR) Rôle d'administrateur, qui an un contrôle total sur le système.
     */
    ADMIN("Admin");

    /**
     * Role name, used for displaying purposes or external references.
     * (FR) Nom du rôle, utilisé à des fins d'affichage ou de références externes.
     * -- GETTER --
     *  Get the role name for display or external references.
     *  (FR) Obtenir le nom du rôle pour l'affichage ou les références externes.
     *
     * @return The display name of the role.

     */
    private final String roleName;

    /**
     * Constructor for creating a UserRole with a display name.
     * (FR) Constructeur pour créer un UserRole avec un nom d'affichage.
     *
     * @param roleName Name of the role.
     */
    UserRole(String roleName) {
        this.roleName = roleName;
    }

}
