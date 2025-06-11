<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" >
<head>
  <meta charset="UTF-8">
  <title>ADD Item Details</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">
  <link rel="stylesheet" href="css/add-item-details.css">

</head>
<body>
<!-- partial:index.partial.html -->
<div class="container">
  <div class="text">
    Add Item
  </div>
  <form action="/item-service/ItemDetailsController">
    <div class="form-row">
      <div class="input-data">
        <input type="text" required name="itemDesc">
        <div class="underline"></div>
        <label>Description</label>
      </div>
      <div class="input-data">
        <input type="text" required name="itemBrand">
        <div class="underline"></div>
        <label>Brand</label>
      </div>
    </div>
    <div class="form-row">
      <div class="input-data">
        <input type="text" required name="itemCategory">
        <div class="underline"></div>
        <label>Category</label>
      </div>
   <input type="hidden" required name="itemId" value="${param.id}">
   <input type="hidden" required name="action" value="add-details">
   
    </div>
    <input type="submit" value="Add" class="button">
  </form>

  <p class="back">
    <a href="/item-service/ItemController?action=load-items" >Back To Items</a>
  </p>
</div>
<!-- partial -->

</body>
</html>