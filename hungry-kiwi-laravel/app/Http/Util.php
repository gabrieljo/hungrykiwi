<?php
/**
 * Created by PhpStorm.
 * User: user
 * Date: 10/10/2016
 * Time: 6:39 PM
 */

namespace App\Http;


use Intervention\Image\Facades\Image;

class Util{
    const POST = 'images/post/';
    const USER = 'images/user/';
    const RESTR = 'images/restr/';


    public static function saveImage($file, $path) {
        self::createDir($path);
        $filename = $path.time() . '_' . str_random(30) . '.png';
        Image::make($file)->resize(300, 200)->save($filename);
        return $filename;
    }

    public static function saveBigImage($file, $path) {
        self::createDir($path);
        $filename = $path.time() . '_' . str_random(30) . '.png';
        Image::make($file)->resize(1000, 800)->save($filename);
        return $filename;
    }

    public static function getFullPath($path) {

        switch($path) {
            case self::POST :
                $uri = self::POST;
                break;
            case self::USER :
                $uri = self::USER;
                break;
            case self::RESTR :
                $uri = self::RESTR;
                break;
        }
        return $uri;
    }

    public static function createDir($path) {
        if (!file_exists($path)) {
            mkdir($path, 0755, true);
        }
    }
}