<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateRecipesTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('recipes', function (Blueprint $table) {
            $table->increments('id');
            $table->integer('user_id') -> unsigned() -> index();
            $table->string('name');
            $table->tinyInteger('type');
            $table->tinyInteger('is_vegi');
            $table->Integer('require_min');
            $table->Integer('comment_num');
            $table->Integer('view');
            $table->float('rating');
            $table->string('ingredient');
            $table->text('content');
            $table->string('img');
            $table->tinyInteger('is_img');
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('recipes');
    }
}
