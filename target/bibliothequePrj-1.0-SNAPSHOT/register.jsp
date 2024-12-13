<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inscription</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        /* Set background color for the entire page */
        body, html {
            height: 100%;
            margin: 0;
            background-color: #E8ECEF; /* Light grey background color */
        }
        .vh-100 {
            min-height: 100%;
        }
    </style>
</head>
<body>
    <section class="vh-100">
        <div class="container py-5 h-100">
            <div class="row d-flex justify-content-center align-items-center h-100">
                <div class="col col-xl-10">
                    <div class="card" style="border-radius: 1rem;">
                        <div class="row g-0">
                            <div class="col-md-6 col-lg-5 d-none d-md-block">
                                <img src="https://i.pinimg.com/736x/b2/d8/45/b2d8452ab86eb7392cfa64c424eacd7b.jpg"
                                     alt="register form" class="img-fluid" style="border-radius: 1rem 0 0 1rem;" />
                            </div>
                            <div class="col-md-6 col-lg-7 d-flex align-items-center">
                                <div class="card-body p-4 p-lg-5 text-black">
                                    <form action="RegisterServlet" method="post">
                                        <div class="d-flex align-items-center mb-3 pb-1">
                                            <i class="fas fa-user-plus fa-2x me-3" style="color: #ff6219;"></i>
                                            <span class="h1 fw-bold mb-0">Logo</span>
                                        </div>
                                        <h5 class="fw-normal mb-3 pb-3" style="letter-spacing: 1px;">Créez votre compte</h5>
                                        <div class="form-outline mb-4">
                                            <input type="text" id="form3Example1" class="form-control form-control-lg" name="nom" required />
                                            <label class="form-label" for="form3Example1">Nom</label>
                                        </div>
                                        <div class="form-outline mb-4">
                                            <input type="email" id="form3Example2" class="form-control form-control-lg" name="email" required />
                                            <label class="form-label" for="form3Example2">Adresse email</label>
                                        </div>
                                        <div class="form-outline mb-4">
                                            <input type="password" id="form3Example3" class="form-control form-control-lg" name="password" required />
                                            <label class="form-label" for="form3Example3">Mot de passe</label>
                                        </div>
                                        <div class="form-outline mb-4">
                                            <select id="form3Example4" class="form-select form-control-lg" name="role" required>
                                                <option value="utilisateur">Utilisateur</option>
                                                <option value="bibliothecaire">Bibliothécaire</option>
                                            </select>
                                            <label class="form-label" for="form3Example4">Rôle</label>
                                        </div>
                                        <div class="pt-1 mb-4">
                                            <button class="btn btn-dark btn-lg btn-block" type="submit">S'inscrire</button>
                                        </div>
                                        <p class="mb-5 pb-lg-2" style="color: #393f81;">Déjà inscrit ? <a href="login.jsp" style="color: #393f81;">Connectez-vous ici</a></p>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
