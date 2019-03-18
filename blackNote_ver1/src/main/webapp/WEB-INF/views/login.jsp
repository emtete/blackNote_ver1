<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>



<!-- header -->

<%@ include file="./includes/header.jsp"%>

<div class="container-fluid">
	<h1 class="mt-4">Login</h1>

	<div class="container">
    <div class="row">
      <div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
        <div class="card card-signin my-5">
          <div class="card-body">
            <h5 class="card-title text-center">Sign In</h5>
            <form method="post" action="/login" class="form-signin">
              <div class="form-label-group">
                <input type="text" name="username" class="form-control" placeholder="id" required autofocus>
                <label for="inputId">ID</label>
              </div>

              <div class="form-label-group">
                <input type="password" name="password" class="form-control" placeholder="Password" required>
                <label for="inputPassword">Password</label>
              </div>

              <div class="custom-control custom-checkbox mb-3">
                <input type="checkbox" class="custom-control-input" id="customCheck1">
                <label class="custom-control-label" for="customCheck1">Remember password</label>
              </div>
              <button class="btn btn-lg btn-primary btn-block text-uppercase" type="submit">Sign in</button>
              
              <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
              
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<%@ include file="./includes/footer.jsp"%>