<?php

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| This file is where you may define all of the routes that are handled
| by your application. Just tell Laravel the URIs it should respond
| to using a Closure or controller method. Build something great!
|
*/
use App\Http\Middleware\Authentication;

Route::get('/', function () {
    return view('welcome');
});
Route::group(['prefix' => '/api/v1'], function() {
    Route::post('/login', 'Auth\AuthController@postLogin');
    Route::get('/images/post/{imgs}', 'ImageController@post');
});


Route::group(['prefix' => '/api/v1', 'middleware' => 'hungry_token'], function() {
    Route::resource('post', 'PostController', ['only' => ['index', 'store', 'edit', 'destroy']]);
    Route::resource('post.comment', 'PostCommentController', ['only' => ['index', 'store', 'edit', 'destroy']]);
    Route::resource('post.like', 'PostLikeController', ['only' => ['index', 'store']]);
    Route::resource('restr', 'RestrController', ['only' => ['index', 'store']]);
    Route::resource('recipe', 'RecipeController', ['only' => ['index', 'show', 'store', 'edit', 'destroy']]);
    Route::resource('restr.comment', 'RestrCommentController', ['only' => ['index', 'store', 'edit', 'destroy']]);
    Route::resource('recipe.comment', 'RecipeCommentController', ['only' => ['index', 'store', 'edit', 'destroy']]);
});



