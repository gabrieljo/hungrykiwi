 <?php

use Illuminate\Database\Seeder;

class DatabaseSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
         $this->call(UserSeeder::class);
         $this->call(PostSeeder::class);
         $this->call(RestrSeeder::class);
         $this->call(RestrCommentSeeder::class);
         $this->call(RecipeSeeder::class);
         $this->call(RecipeCommentSeeder::class);
         $this->call(PostCommentSeeder::class);
    }
}
