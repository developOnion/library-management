package com.oop.library_management.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PostPersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "members")
public class Member extends User {

  private String membershipNumber;

  protected Member() {
  }

  public Member(
      String username,
      String password,
      String firstName,
      String lastName) {
    super(username, password, firstName, lastName);
  }

  @PostPersist
  private void generateMembershipNumber() {
    this.membershipNumber = getMembershipNumber();
  }

  // derived from id
  public String getMembershipNumber() {

    if (getId() == null) {
      return null;
    }

    return "MEM-" + String.format("%05d", getId());
  }
}
