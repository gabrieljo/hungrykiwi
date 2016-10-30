<?php

use Illuminate\Database\Seeder;

class RestrSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        factory(App\Restr::class, 100) -> create();
    }
}
