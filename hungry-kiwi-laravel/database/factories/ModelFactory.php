<?php

/*
|--------------------------------------------------------------------------
| Model Factories
|--------------------------------------------------------------------------
|
| Here you may define all of your model factories. Model factories give
| you a convenient way to create models for testing and seeding your
| database. Just tell the factory how a default model should look.
|
*/

$factory->define(App\User::class, function(Faker\Generator $faker) {
    return [
        'email' => $faker->unique()->safeEmail,
        'first_name' => $faker->firstName,
        'last_name' => $faker->lastName,
        'is_restr' => $faker->numberBetween(0, 1),
        'img' => 'http://img',
        'cover_img' => 'http://cover',
        'provider' => $faker->numberBetween(0, 1),
    ];
});

$factory->define(App\Post::class, function(Faker\Generator $faker) {
    return [
        'user_id' => $faker->numberBetween(1, 100),
        'restr_id' => $faker->numberBetween(0, 2),
        'content' => $faker->paragraphs[1],
        'like_num' => 0,
        'comment_num' => 0,
        'is_img' => $faker->numberBetween(0, 1),
    ];
});


$factory->define(App\Restr::class, function(Faker\Generator $faker) {
    return [
        'user_id' => $faker->numberBetween(1, 100),
        'name' => 'restaurant_name',
        'img' => 'http://img',
        'intro' => $faker->paragraphs[1],
        'region' => $faker->city,
        'town' => $faker->city,
        'street' => $faker->streetName,
        'phone' => $faker->phoneNumber,
        'mobile' => $faker->phoneNumber,
        'rating' => $faker->randomFloat(1, 0, 5),
        'views' => $faker->numberBetween(0, 100000),
        'lat' => $faker->randomFloat(8, -48, -35),
        'long' => $faker->randomFloat(8, 166, 178),
        'is_img' => $faker->numberBetween(0, 1),
        'menu_table' => 'table',
    ];
});

$factory->define(App\Recipe::class, function(Faker\Generator $faker) {
    return [
        'user_id' => $faker->numberBetween(90, 100),
        'name' => $faker->country,
        'type' => $faker->numberBetween(0, 5),
        'is_vegi' => $faker->numberBetween(0, 1),
        'require_min' => $faker->numberBetween(0, 120),
        'comment_num' => $faker->numberBetween(0, 120),
        'rating' => $faker->randomFloat(1, 0, 5),
        'view' => $faker->numberBetween(0, 1000),
        'ingredient' => $faker->city,
        'content' => $faker->paragraphs[0],
        'img' => "http://img",
        'is_img' => 0,
    ];
});
$factory->define(App\RecipeComment::class, function(Faker\Generator $faker) {
    return [
        'user_id' => $faker->numberBetween(1, 100),
        'recipe_id' => $faker->numberBetween(1, 100),
        'rate' => $faker->randomFloat(1, 0, 5),
        'comment' =>  $faker->paragraphs[1],
    ];
});
$factory->define(App\RestrComment::class, function(Faker\Generator $faker) {
    return [
        'user_id' => $faker->numberBetween(90, 100),
        'restr_id' => $faker->numberBetween(90, 100),
        'rate' => $faker->randomFloat(1, 0, 5),
        'comment' =>  $faker->paragraphs[1],
    ];
});
$factory->define(App\PostComment::class, function(Faker\Generator $faker) {
    return [
        'user_id' => $faker->numberBetween(90, 100),
        'post_id' => $faker->numberBetween(90, 100),
        'comment' =>  $faker->paragraphs[1],
    ];
});