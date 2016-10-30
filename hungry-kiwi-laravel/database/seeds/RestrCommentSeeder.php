<?php

use Illuminate\Database\Seeder;

class RestrCommentSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        factory(App\RestrComment::class, 1000) -> create();
    }
}
