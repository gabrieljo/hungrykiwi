<?php

use Illuminate\Database\Seeder;

class RecipeCommentSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        factory(App\RecipeComment::class, 100) -> create();
    }
}
